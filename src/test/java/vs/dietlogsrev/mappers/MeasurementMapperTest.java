package vs.dietlogsrev.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import vs.dietlogsrev.entity.Measurement;
import vs.dietlogsrev.model.CreateMeasurementRequest;

class MeasurementMapperTest {
	
	MeasurementMapper mapper = Mappers.getMapper(MeasurementMapper.class);

	@Test
	@DisplayName("Map a measurement to a measurement response")
	void testMapMeasurementToMeasurementResponse() {
		var measurement = new Measurement(new CreateMeasurementRequest(LocalDate.now(), BigDecimal.TEN), BigDecimal.TEN);
		var measurementResponse = mapper.toMeasurementResponse(measurement);
		
		assertThat(measurementResponse).isNotNull();
		assertThat(measurementResponse.bmi()).isEqualByComparingTo(measurement.getBmi());
		assertThat(measurementResponse.date()).isEqualTo(measurement.getDateOf());
		assertThat(measurementResponse.weight()).isEqualByComparingTo(measurement.getBmi());
	}

}
