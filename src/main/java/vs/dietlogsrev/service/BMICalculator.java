package vs.dietlogsrev.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class BMICalculator {

    public BigDecimal calculate(BigDecimal weight, BigDecimal height) {
        if (height.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Height cannot be negative");
        }
        return weight.divide(height.pow(2), 1, RoundingMode.HALF_UP).setScale(1);
    }

}
