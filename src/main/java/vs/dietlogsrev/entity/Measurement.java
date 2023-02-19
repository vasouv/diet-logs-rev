package vs.dietlogsrev.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.model.MeasurementResponse;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dateOf", nullable = false)
    @NotNull
    private LocalDate dateOf;

    @Column(name = "weight", nullable = false)
    @NotNull
    private BigDecimal weight;

    @Column(name = "bmi", nullable = false)
    @NotNull
    private BigDecimal bmi;

    @ManyToOne
    private User user;
    
    public Measurement() {}

	public Measurement(CreateMeasurementRequest request, BigDecimal bmi) {
        this.dateOf = request.date();
        this.weight = request.weight();
        this.bmi = bmi;
    }
    
    public LocalDate getDateOf() {
		return dateOf;
	}

	public void setDateOf(LocalDate dateOf) {
		this.dateOf = dateOf;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getBmi() {
		return bmi;
	}

	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
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
		return "Measurement [id=" + id + ", dateOf=" + dateOf + ", weight=" + weight + ", bmi=" + bmi + "]";
	}

	public MeasurementResponse toMeasurementResponse() {
        return new MeasurementResponse(dateOf, weight, bmi);
    }

}
