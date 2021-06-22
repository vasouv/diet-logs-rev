package vs.dietlogsrev.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vs.dietlogsrev.entity.Measurement;
import vs.dietlogsrev.entity.User;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findAllByUser(User user);

}
