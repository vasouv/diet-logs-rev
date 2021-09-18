package vs.dietlogsrev.service;

import javax.validation.Valid;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vs.dietlogsrev.entity.UserInfo;
import vs.dietlogsrev.model.CreateUserInfoRequest;
import vs.dietlogsrev.model.UserFullInfo;
import vs.dietlogsrev.model.UserNeededInfo;
import vs.dietlogsrev.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserService userService;
    private final UserInfoRepository infoRepository;
    private final MeasurementService measurementService;

    public void saveInfo(int userId, @Valid CreateUserInfoRequest request) {

        var user = userService.findById(userId);

        var userInfo = new UserInfo(request);
        userInfo.setUser(user);

        infoRepository.save(userInfo);
    }

    public UserFullInfo findFullInfo(int userId) {

        // find user
        var user = userService.findById(userId);

        // find user info
        var userInfo = infoRepository.findByUserId(userId);
        
        // find user measurements
        var measurements = measurementService.findByUserId(userId);

        // construct final object
        return UserFullInfo.builder().email(user.getEmail()).username(user.getUsername())
                .info(new UserNeededInfo(userInfo)).measurements(measurements).build();
    }

}
