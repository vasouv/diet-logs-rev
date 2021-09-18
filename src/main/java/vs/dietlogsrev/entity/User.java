package vs.dietlogsrev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vs.dietlogsrev.model.CreateUserRequest;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Provide an email address")
    @NotBlank
    private String email;

    @Setter
    @Column(name = "username", nullable = false)
    @NotBlank
    private String username;

    @Setter
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 8, max = 24, message = "Password length between 8 and 24 characters")
    private String password;
    
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public User(CreateUserRequest request) {
        this.email = request.email();
        this.username = request.username();
        this.password = request.password();
    }

}
