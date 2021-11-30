package vs.dietlogsrev.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vs.dietlogsrev.model.CreateUserInfoRequest;
import vs.dietlogsrev.model.Gender;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Setter
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;
    
    @Setter
    @Column(name = "surname", nullable = false)
    @NotNull
    private String surname;
    
    @Setter
    @Column(name = "gender", nullable = false)
    @NotNull
    private Gender gender;
    
    @Setter
    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;
    
    @Setter
    @Column(name = "height", nullable = false)
    @NotNull
    private BigDecimal height;
    
    @Setter
    @Column(name = "weight", nullable = false)
    @NotNull
    private BigDecimal weight;
    
    @Setter
    @OneToOne
    private User user;
    
    public UserInfo(CreateUserInfoRequest request) {
        this.name = request.name();
        this.surname = request.surname();
        this.gender = request.gender();
        this.dateOfBirth = request.dateOfBirth();
        this.height = request.height();
        this.weight = request.weight();
    }

    public UserInfo(int id, String name, String surname, Gender gender,
            LocalDate dateOfBirth, BigDecimal height, BigDecimal weight) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
    }

}
