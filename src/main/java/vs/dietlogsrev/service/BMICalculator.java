package vs.dietlogsrev.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class BMICalculator {

    public BigDecimal calculate(BigDecimal weight, BigDecimal height) {
        if (height.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Height cannot be negative");
        }
        return weight.divide(BigDecimal.valueOf(Math.pow(height.doubleValue(), 2)));
    }

}
