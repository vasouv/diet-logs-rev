package vs.dietlogsrev.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vs.dietlogsrev.entity.User;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.CreateUserRequest;
import vs.dietlogsrev.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Find all users - empty list")
    void findAllUsersEmptyList() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(userService.findAll()).isEmpty();

    }

    @Test
    @DisplayName("Find all users")
    void findAllUsers() {
        var users = List.of(new User("email", "username","password"));
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("Find user by id - user not found")
    void findUserByIdThrowsUserNotFound() {
        doThrow(new UserNotFoundException()).when(userRepository).findById(anyInt());
        assertThrows(UserNotFoundException.class, () -> userService.findById(anyInt()));
    }

    @Test
    @DisplayName("Find user by id")
    void findUserById() {
        var user = new User(1, "email", "username", "password");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        var foundUser = userService.findById(1);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save new user - user exists")
    void saveNewUserThrowsUserExistsException() {
        var request = new CreateUserRequest("email", "username", "password");
        when(userRepository.existsByEmail(request.email())).thenThrow(new EntityExistsException());
        assertThrows(EntityExistsException.class, () -> userService.save(request));
    }

    @Test
    @DisplayName("Save new user - return new user")
    void saveNewUser() {
        var request = new CreateUserRequest("email", "username", "password");
        doReturn(new User(1, "email", "username", "password")).when(userRepository).save(any());
        
        var savedUser = userService.save(request);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isPositive();
    }
}
