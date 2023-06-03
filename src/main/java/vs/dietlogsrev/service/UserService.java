package vs.dietlogsrev.service;

import java.util.List;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
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

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(@Valid CreateUserRequest createUserRequest) {
        
        if (userRepository.existsByEmail(createUserRequest.email())) {
            log.warn("User with email: {} already exists", createUserRequest.email());
            throw new EntityExistsException(ErrorMessage.EMAIL_EXISTS);
        }
        
        return userRepository.save(new User(createUserRequest));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
