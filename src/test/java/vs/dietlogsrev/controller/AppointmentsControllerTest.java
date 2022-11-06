package vs.dietlogsrev.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import vs.dietlogsrev.entity.Appointment;
import vs.dietlogsrev.exception.AppointmentDateInFutureException;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.AppointmentResponse;
import vs.dietlogsrev.model.CreateAppointmentRequest;
import vs.dietlogsrev.service.AppointmentService;

@WebMvcTest(controllers = AppointmentsController.class)
public class AppointmentsControllerTest {

    @Autowired 
    MockMvc mvc;

    @MockBean
    AppointmentService appointmentService;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("Create appointment")
    public void createAppointment() throws Exception {

        var appointmentRequest = new CreateAppointmentRequest(LocalDate.now().plusDays(1), 1);

        mvc.perform(post("/appointments/{id}", 1)
                .content(mapper.writeValueAsString(appointmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Create appointment - date in past - throws exception")
    public void createAppointmentDateInPastException() throws Exception {

        var appointmentRequest = new CreateAppointmentRequest(LocalDate.now().minusDays(1), 1);

        doThrow(new AppointmentDateInFutureException()).when(appointmentService).add(1, appointmentRequest);

        mvc.perform(post("/appointments/{userId}", 1)
                .content(mapper.writeValueAsString(appointmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());

    }

    @Test
    @DisplayName("Create appointment - date is null - throws exception")
    public void createAppointmentDateIsNull() throws Exception {
        var appointmentRequest = new CreateAppointmentRequest(null, 1);

        doThrow(new AppointmentDateInFutureException()).when(appointmentService).add(1, appointmentRequest);

        mvc.perform(post("/appointments/{userId}", 1)
                .content(mapper.writeValueAsString(appointmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @DisplayName("Create appointment - user not found")
    public void createAppointmentUserNotFound() throws Exception {
        CreateAppointmentRequest appointmentRequest = new CreateAppointmentRequest(LocalDate.now(), 1);

        doThrow(new UserNotFoundException()).when(appointmentService).add(1, appointmentRequest);

        mvc.perform(post("/appointments/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(appointmentRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @DisplayName("Find appointments by user id - user not found")
    public void findByUserIdUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(appointmentService).findByUserId(1);
        
        mvc.perform(get("/appointments/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @DisplayName("Find appointments by user id - empty list")
    public void findByUserIdEmptyCollection() throws Exception {
        
        when(appointmentService.findByUserId(1)).thenReturn(Collections.emptyList());
        
        mvc.perform(get("/appointments/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Find appointments by user id")
    public void findByUserId() throws Exception {

        var appointments = List.of(new AppointmentResponse(LocalDate.now()));
        
        when(appointmentService.findByUserId(1)).thenReturn(appointments);
        
        mvc.perform(get("/appointments/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty());
    }

}
