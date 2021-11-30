package vs.dietlogsrev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vs.dietlogsrev.entity.UserInfo;
import vs.dietlogsrev.exception.UserInfoNotFoundException;
import vs.dietlogsrev.exception.UserNotFoundException;
import vs.dietlogsrev.model.Gender;
import vs.dietlogsrev.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @Mock
    UserService userService;

    @Mock
    UserInfoRepository infoRepository;

    @InjectMocks
    UserInfoService userInfoService;

    @Test
    @DisplayName("Find by user id - user info not found")
    void findByUserIdUserInfoNotFound() {
        when(infoRepository.findByUserId(anyInt())).thenThrow(new UserInfoNotFoundException());
        assertThrows(UserInfoNotFoundException.class, () -> userInfoService.findByUserId(anyInt()));
    }

    @Test
    @DisplayName("Find by user id")
    void findByUserId() {
        var userInfo = new UserInfo(1, "name", "surname", Gender.M, LocalDate.of(1988, Month.MAY, 5), new BigDecimal("1.78"), new BigDecimal("98.2")); 
        var optionalUserInfo = Optional.of(userInfo);

        when(infoRepository.findByUserId(anyInt())).thenReturn(optionalUserInfo);

        assertThat(userInfoService.findByUserId(anyInt())).isNotNull();
    }

    @Test
    @DisplayName("Save info for user - user not found")
    void saveInfoForUserThrowsUserNotFoundException() {
        when(userService.findById(anyInt())).thenThrow(new UserNotFoundException());
        assertThrows(UserNotFoundException.class, () -> userInfoService.saveInfo(anyInt(), null));
    }

    @Test
    @DisplayName("Save info for user")
    void saveInfoForUser() {
        fail();
    }

    @Test
    @DisplayName("Find full info for user - user not found")
    void findFullInfoForUserThrowsUserNotFoundException() {
        fail();
    }

    @Test
    @DisplayName("Find full info for user - user info not found")
    void findFullInfoForUserThrowsUserInfoNotFound() {
        fail();
    }

    @Test
    @DisplayName("Find full info for user")
    void findFullInfoForUser() {
        fail();
    }

}
