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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.model.MeasurementResponse;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(name = "dateOf", nullable = false)
    @NotNull
    private LocalDate dateOf;

    @Setter
    @Column(name = "weight", nullable = false)
    @NotNull
    private BigDecimal weight;

    @Setter
    @Column(name = "bmi", nullable = false)
    @NotNull
    private BigDecimal bmi;

    @Setter
    @ManyToOne
    private User user;

    public Measurement(CreateMeasurementRequest request, BigDecimal bmi) {
        this.dateOf = request.date();
        this.weight = request.weight();
        this.bmi = bmi;
    }
    
    public MeasurementResponse toMeasurementResponse() {
        return new MeasurementResponse(dateOf, weight, bmi);
    }

}
