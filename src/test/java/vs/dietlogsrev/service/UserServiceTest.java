package vs.dietlogsrev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.List;

import jakarta.persistence.EntityExistsException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vs.dietlogsrev.entity.User;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.CreateUserRequest;
import vs.dietlogsrev.repository.UserRepository;

@Disabled
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Find all users - empty list")
    void findAllUsersEmptyList() {
        assertThat(userService.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Find all users")
    void findAllUsers() {
        initDatabase();

        assertThat(userService.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("Find user by id - user not found")
    void findUserByIdThrowsUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(anyInt()));
    }

    @Disabled
    @Test
    @DisplayName("Find user by id")
    void findUserById() {
        initDatabase();

        var foundUser = userService.findById(1);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save new user - user exists")
    void saveNewUserThrowsUserExistsException() {
        initDatabase();

        var request = new CreateUserRequest("vasouv@email.com", "vasouv", "password");
        assertThrows(EntityExistsException.class, () -> userService.save(request));
    }

    @Test
    @DisplayName("Save new user - return new user")
    void saveNewUser() {
        var request = new CreateUserRequest("email@email.com", "username", "password");

        var savedUser = userService.save(request);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isPositive();
    }

    private void initDatabase() {
        var users = List.of(new User("vasouv@email.com", "vasouv", "password"),
                new User("user@email.com", "user", "password"));
        userRepository.saveAll(users);
    }
}
