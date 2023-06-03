package vs.dietlogsrev.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

public record CreateMeasurementRequest(
        @NotNull(message = "Date is required") LocalDate date,
        @NotNull(message = "Weight is required") BigDecimal weight) {

}
