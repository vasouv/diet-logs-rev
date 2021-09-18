package vs.dietlogsrev.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserInfoRequest(String name, String surname, Gender gender,
        LocalDate dateOfBirth, BigDecimal height, BigDecimal weight) {

}
