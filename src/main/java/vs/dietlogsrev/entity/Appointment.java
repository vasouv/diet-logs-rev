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

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dateOf")
    @NotNull
    private LocalDate dateOf;

    @ManyToOne
    private User user;
    
    public Appointment() {}

    public Appointment(LocalDate dateOf) {
        this.dateOf = dateOf;
    }
    
    public LocalDate getDateOf() {
		return dateOf;
	}

	public void setDateOf(LocalDate dateOf) {
		this.dateOf = dateOf;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", dateOf=" + dateOf + ", user=" + user + "]";
	}

}
