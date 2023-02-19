package vs.dietlogsrev.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import vs.dietlogsrev.entity.Appointment;

class AppointmentMapperTest {
	
	AppointmentMapper mapper = Mappers.getMapper(AppointmentMapper.class);

	@Test
	@DisplayName("Map an appointment to appointment response")
	void testMapAppointmentToAppointmentResponse() {
		var appointment = new Appointment(LocalDate.now());
		var appointmentResponse = mapper.toAppointmentResponse(appointment);
		
		assertThat(appointmentResponse).isNotNull();
		assertThat(appointmentResponse.dateOf()).isEqualTo(appointment.getDateOf());
	}

}
