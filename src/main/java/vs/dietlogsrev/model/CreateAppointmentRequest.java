package vs.dietlogsrev.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CreateAppointmentRequest(
    @NotNull(message = "Date is required") LocalDate date,
    @NotNull(message = "User ID is required") Integer userId) {

}
