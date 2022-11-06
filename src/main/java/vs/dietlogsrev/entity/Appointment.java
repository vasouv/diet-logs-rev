package vs.dietlogsrev.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vs.dietlogsrev.model.AppointmentResponse;

@Entity
@Table(name = "appointments")
@Getter
@ToString
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(name = "dateOf")
    @NotNull
    private LocalDate dateOf;

    @Setter
    @ManyToOne
    private User user;

    public Appointment(LocalDate dateOf) {
        this.dateOf = dateOf;
    }
    
    public AppointmentResponse toAppointmentResponse() {
        return new AppointmentResponse(dateOf);
    }

}
