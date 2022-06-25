package vs.dietlogsrev.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import vs.dietlogsrev.exception.MeasurementDateInFutureException;
import vs.dietlogsrev.exception.MeasurementWeightNegativeOrZero;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.model.MeasurementResponse;
import vs.dietlogsrev.service.MeasurementService;

@WebMvcTest(controllers = MeasurementsController.class)
public class MeasurementsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MeasurementService measurementService;

    @Test
    @DisplayName("Create measurement")
    public void createMeasurement() throws Exception {
        var measurementRequest = new CreateMeasurementRequest(LocalDate.now(), new BigDecimal("88"));

        mvc.perform(post("/measurements/{userId}", 1)
                .content(mapper.writeValueAsString(measurementRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create measurement - date in future - throws exception")
    public void createMeasurementDateInFuture() throws Exception {
        var measurementRequest = new CreateMeasurementRequest(LocalDate.now().plusDays(1), new BigDecimal("88"));

        doThrow(new MeasurementDateInFutureException()).when(measurementService).add(1, measurementRequest);

        mvc.perform(post("/measurements/{userId}", 1)
                .content(mapper.writeValueAsString(measurementRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());

    }

    @ParameterizedTest
    @ValueSource(strings = {"0.00", "-1.0"})
    @DisplayName("Create measurement - weight negative or zero - throws exception")
    public void createMeasurementWeightIsNegativeOrZero(String value) throws Exception {
        var weight = new BigDecimal(value);

        var measurementRequest = new CreateMeasurementRequest(LocalDate.now(), weight);

        doThrow(new MeasurementWeightNegativeOrZero()).when(measurementService).add(1, measurementRequest);

        mvc.perform(post("/measurements/{userId}", 1)
                .content(mapper.writeValueAsString(measurementRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @DisplayName("Find measurements by user id - return empty list")
    public void findByUserIdEmptyCollection() throws Exception {

        when(measurementService.findByUserId(1)).thenReturn(Collections.emptyList());

        mvc.perform(get("/measurements/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Find measurements by user id - returns list")
    public void findByUserId() throws Exception {
        var measurements = List.of(new MeasurementResponse(LocalDate.now(), new BigDecimal("88.0"), new BigDecimal("12.3")));

        when(measurementService.findByUserId(1)).thenReturn(measurements);

        mvc.perform(get("/measurements/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Find measurements by user id - user not found")
    public void findByUserIdUserNotFound() throws Exception {

        doThrow(new UserNotFoundException()).when(measurementService).findByUserId(1);

        mvc.perform(get("/measurements/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.errors").isNotEmpty());
    }

}
