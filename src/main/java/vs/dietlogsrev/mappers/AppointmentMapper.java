package vs.dietlogsrev.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vs.dietlogsrev.entity.Appointment;
import vs.dietlogsrev.model.AppointmentResponse;

@Mapper
public interface AppointmentMapper {
	
	@Mapping(source = "dateOf", target = "dateOf")
	AppointmentResponse toAppointmentResponse(Appointment appointment);

}
