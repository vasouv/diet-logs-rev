package vs.dietlogsrev.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Email is required") @Email(message = "Provide an email address") String email, 
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") @Size(min = 8, max = 24, message = "Password length between 8 and 24 characters") String password) {

}
