package vs.dietlogsrev.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import vs.dietlogsrev.entity.User;
import vs.dietlogsrev.model.CreateUserRequest;
import vs.dietlogsrev.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(HttpServletRequest httpRequest, @Valid @RequestBody CreateUserRequest request) {
        var savedUser = this.userService.save(request);
        var savedUserURI = UriComponentsBuilder.fromUriString(httpRequest.getServletPath()).path("/" + savedUser.getId()).build().toUri();
        return ResponseEntity.created(savedUserURI).build();
    }

}
