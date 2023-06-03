package vs.dietlogsrev.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vs.dietlogsrev.entity.UserInfo;
import vs.dietlogsrev.exception.UserInfoNotFoundException;
import vs.dietlogsrev.model.CreateUserInfoRequest;
import vs.dietlogsrev.model.UserFullInfo;
import vs.dietlogsrev.model.UserNeededInfo;
import vs.dietlogsrev.repository.UserInfoRepository;

@Service
public class UserInfoService {

    private final UserService userService;
    private final UserInfoRepository infoRepository;

    public UserInfoService(UserService userService, UserInfoRepository infoRepository) {
		super();
		this.userService = userService;
		this.infoRepository = infoRepository;
	}

	public void saveInfo(int userId, @Valid CreateUserInfoRequest request) {

        var user = userService.findById(userId);

        var userInfo = new UserInfo(request);
        userInfo.setUser(user);

        infoRepository.save(userInfo);
    }
    
    public UserInfo findByUserId(int userId){
        return infoRepository.findByUserId(userId).orElseThrow(UserInfoNotFoundException::new);
    }

    public UserFullInfo findFullInfo(int userId) {

        // find user
        var user = userService.findById(userId);

        // find user info
        var userInfo = infoRepository.findByUserId(userId);
        
        // construct final object
        var info = new UserFullInfo(user.getUsername(), user.getEmail());
        if(userInfo.isPresent()) {
            info.setInfo(new UserNeededInfo(userInfo.get()));
        }
        
        return info;
    }

}
