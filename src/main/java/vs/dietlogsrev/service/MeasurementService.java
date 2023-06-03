package vs.dietlogsrev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vs.dietlogsrev.entity.Measurement;
import vs.dietlogsrev.exception.ErrorMessage;
import vs.dietlogsrev.exception.MeasurementDateInFutureException;
import vs.dietlogsrev.exception.MeasurementWeightNegativeOrZero;
import vs.dietlogsrev.mappers.MeasurementMapper;
import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.model.MeasurementResponse;
import vs.dietlogsrev.repository.MeasurementRepository;

@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);
    private MeasurementMapper measurementMapper = Mappers.getMapper(MeasurementMapper.class);
    
    private final MeasurementRepository measurementRepository;
    private final UserService userService;
    private final BMICalculator bmiCalculator;
    private final UserInfoService infoService;
    
    public MeasurementService(MeasurementRepository measurementRepository, UserService userService,
			BMICalculator bmiCalculator, UserInfoService infoService) {
		this.measurementRepository = measurementRepository;
		this.userService = userService;
		this.bmiCalculator = bmiCalculator;
		this.infoService = infoService;
	}

	public void add(int userId, @Valid CreateMeasurementRequest request) {

        // measurement date must be past or present
        if (request.date().isAfter(LocalDate.now())) {
            log.warn(ErrorMessage.MEASUREMENT_DATE_IN_FUTURE);
            throw new MeasurementDateInFutureException();
        }

        // weight must be positive
        if (request.weight().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn(ErrorMessage.MEASUREMENT_WEIGHT_NEGATIVE_OR_ZERO);
            throw new MeasurementWeightNegativeOrZero();
        }
        
        // find user by id
        var user = userService.findById(userId);
        
        // find user info
        var userInfo = infoService.findByUserId(userId);
        
        // create measurement and save
        var measurement = new Measurement(request, bmiCalculator.calculate(request.weight(), userInfo.getHeight()));
        measurement.setUser(user);
        measurementRepository.save(measurement);
        
    }

    public List<MeasurementResponse> findByUserId(int userId) {
        var user = userService.findById(userId);
        return measurementRepository.findAllByUser(user).stream().map(measurementMapper::toMeasurementResponse).toList();
    }

}
