package vs.dietlogsrev.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.CreateAppointmentRequest;
import vs.dietlogsrev.service.AppointmentService;

@WebMvcTest(controllers = AppointmentsController.class)
public class AppointmentsControllerTest {

    @Autowired MockMvc mvc;

    @MockBean AppointmentService appointmentService;

    @Test
    @DisplayName("Create appointment - date in future")
    public void createAppointment() {
        fail("Not done yet");
    }

    @Test
    @DisplayName("Create appointment - date in past - throws exception")
    public void createAppointmentDateInPastException() {
        fail("Not done yet");
    }

    @Test
    @DisplayName("Create appointment - date is null - throws exception")
    public void createAppointmentDateIsNull() {
        fail("Not done yet");
    }

    @Test
    @DisplayName("Create appointment - date is empty - throws exception")
    public void createAppointmentDateIsEmpty() {
        fail("Not done yet");
    }

    @Test
    @DisplayName("Create appointment - user not found")
    public void createAppointmentUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(appointmentService).add(anyInt(), new CreateAppointmentRequest(null, null));

        mvc.perform(post("/appointments/{userId}", anyInt()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

}
