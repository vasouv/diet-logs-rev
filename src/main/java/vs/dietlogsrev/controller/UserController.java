package vs.dietlogsrev.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<User> create(HttpServletRequest httpRequest, @Valid @RequestBody CreateUserRequest request) {
        var savedUser = userService.save(request);
        var savedUserURI = UriComponentsBuilder.fromUriString(httpRequest.getServletPath()).path("/" + savedUser.getId()).build().toUri();
        return ResponseEntity.created(savedUserURI).build();
    }
    
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

}
