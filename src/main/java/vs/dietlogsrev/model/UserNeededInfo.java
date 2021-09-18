package vs.dietlogsrev.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import vs.dietlogsrev.entity.UserInfo;

@Data
@NoArgsConstructor
public class UserNeededInfo {
    
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate dateOfBirth;
    private BigDecimal height;
    private BigDecimal weight;
    
    public UserNeededInfo(UserInfo user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.height = user.getHeight();
        this.weight = user.getWeight();
    }

}
