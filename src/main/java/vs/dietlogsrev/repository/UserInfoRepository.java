package vs.dietlogsrev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vs.dietlogsrev.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    
    UserInfo findByUserId(int userId);

}
