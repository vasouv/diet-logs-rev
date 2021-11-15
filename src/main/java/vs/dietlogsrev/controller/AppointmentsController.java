package vs.dietlogsrev.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import vs.dietlogsrev.model.CreateAppointmentRequest;
import vs.dietlogsrev.service.AppointmentService;

@RestController
@RequestMapping("appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;

    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable("userId") int userId, @Valid @RequestBody CreateAppointmentRequest request) {
        appointmentService.add(userId, request);
    }

}
