package vs.dietlogsrev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vs.dietlogsrev.exception.ErrorMessage;
import vs.dietlogsrev.exception.MeasurementDateInFutureException;
import vs.dietlogsrev.exception.MeasurementWeightNegativeOrZero;
import vs.dietlogsrev.model.CreateMeasurementRequest;

@Service
public class MeasurementService {

    private static final Logger log = LoggerFactory.getLogger(MeasurementService.class);

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

    }

}
