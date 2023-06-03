package vs.dietlogsrev.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Email is required") @Email(message = "Provide an email address") String email, 
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") @Size(min = 8, max = 24, message = "Password length between 8 and 24 characters") String password) {

}
