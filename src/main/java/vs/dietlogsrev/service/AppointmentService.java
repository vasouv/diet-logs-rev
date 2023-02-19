package vs.dietlogsrev.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vs.dietlogsrev.entity.Appointment;
import vs.dietlogsrev.exception.AppointmentDateInFutureException;
import vs.dietlogsrev.exception.ErrorMessage;
import vs.dietlogsrev.mappers.AppointmentMapper;
import vs.dietlogsrev.model.AppointmentResponse;
import vs.dietlogsrev.model.CreateAppointmentRequest;
import vs.dietlogsrev.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);

    final AppointmentRepository appointmentRepository;
    final UserService userService;
    
    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService) {
		this.appointmentRepository = appointmentRepository;
		this.userService = userService;
	}

	public void add(int userId, @Valid CreateAppointmentRequest request) {

        // appointment must be in the future
        if(request.date().isBefore(LocalDate.now())) {
            log.warn(ErrorMessage.APPOINTMENT_DATE_IN_FUTURE);
            throw new AppointmentDateInFutureException();
        }

        // find user by id
        var user = userService.findById(userId);

        // create appointment and save
        var appointment = new Appointment(request.date());
        appointment.setUser(user);
        appointmentRepository.save(appointment);

    }

    public List<AppointmentResponse> findByUserId(int userId) {
        var user = userService.findById(userId);
        return appointmentRepository.findAllByUser(user).stream().map(appointmentMapper::toAppointmentResponse).toList();
    }

}
