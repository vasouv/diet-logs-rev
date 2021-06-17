package vs.dietlogsrev.service;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vs.dietlogsrev.entity.User;
import vs.dietlogsrev.exception.ErrorMessage;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.CreateUserRequest;
import vs.dietlogsrev.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(@Valid CreateUserRequest createUserRequest) {
        
        if (repository.existsByEmail(createUserRequest.email())) {
            log.warn("User with email: {} already exists", createUserRequest.email());
            throw new EntityExistsException(ErrorMessage.EMAIL_EXISTS);
        }
        
        return repository.save(new User(createUserRequest));
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findById(int id) {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
