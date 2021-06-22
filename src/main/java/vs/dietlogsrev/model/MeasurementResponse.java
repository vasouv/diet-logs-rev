package vs.dietlogsrev.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MeasurementResponse(LocalDate date, BigDecimal weight, BigDecimal bmi) {

}
