package vs.dietlogsrev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BMICalculatorTest {

    BMICalculator calculator = new BMICalculator();

    @Test
    @DisplayName("0 weight returns 0 bmi")
    void weightZeroReturnsZero() {

        var weight = new BigDecimal("0.0");
        var height = new BigDecimal("1.75");

        var result = calculator.calculate(weight, height);
        var expected = new BigDecimal("0.0");

        assertThat(result).isEqualByComparingTo(expected);

    }

    @Test
    @DisplayName("0 height throws IllegalArgumentException")
    void heightZeroThrowsIllegalArgumentException() {
        var weight = new BigDecimal("75.7");
        var height = new BigDecimal("0.00");

        assertThatIllegalArgumentException().isThrownBy(() -> {
            calculator.calculate(weight, height);
        });
    }
    
    @Test
    @DisplayName("105.7 kg and 1.76 height returns 33.9 bmi")
    void correctWeightAndCorrectHeightReturnBMI() {
        var weight = new BigDecimal("105.7");
        var height = new BigDecimal("1.76");
        
        var result = calculator.calculate(weight, height);
        var expected = new BigDecimal("34.1");
        
        assertThat(result).isEqualByComparingTo(expected);
    }

}
