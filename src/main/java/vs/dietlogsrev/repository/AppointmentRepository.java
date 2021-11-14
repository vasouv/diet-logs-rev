package vs.dietlogsrev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vs.dietlogsrev.entity.Appointment;
import vs.dietlogsrev.entity.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    
    List<Appointment> findAllByUser(User user);

}
