package vs.dietlogsrev.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import vs.dietlogsrev.entity.User;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.service.UserInfoService;
import vs.dietlogsrev.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @Autowired MockMvc mvc;

  @MockBean UserService userService;

  @MockBean UserInfoService infoService;

  @Test
  @DisplayName("Find all - returns empty list")
  void findAllReturnsEmptyList() throws Exception {
    when(userService.findAll()).thenReturn(Collections.emptyList());

    mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  @DisplayName("Find all - returns list of users")
  void findAllReturnsListOfUsers() throws Exception {
    var users =
        List.of(
            new User("email", "username", "password"),
            new User("email2", "username2", "password2"));

    when(userService.findAll()).thenReturn(users);

    mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  @DisplayName("Find user by id - returns user")
  void findUserByIdReturnsUser() throws Exception {
    var user = new User(1, "email", "username", "password");
    when(userService.findById(anyInt())).thenReturn(user);

    mvc.perform(get("/users/{id}", anyInt()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(user.getEmail()))
        .andExpect(jsonPath("$.password").value(user.getPassword()))
        .andExpect(jsonPath("$.username").value(user.getUsername()));
  }

  @Test
  @DisplayName("Find user by id - throws Not Found Exception")
  void findUserByIdThrowsNotFoundException() throws Exception {
    when(userService.findById(anyInt())).thenThrow(new UserNotFoundException());

    mvc.perform(get("/users/{id}", 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.errors").isNotEmpty());
  }
}
