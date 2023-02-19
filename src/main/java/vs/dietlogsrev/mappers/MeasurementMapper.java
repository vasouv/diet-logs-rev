package vs.dietlogsrev.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vs.dietlogsrev.entity.Measurement;
import vs.dietlogsrev.model.MeasurementResponse;

@Mapper
public interface MeasurementMapper {
	
	@Mapping(source = "dateOf", target = "date")
	@Mapping(source = "weight", target = "weight")
	@Mapping(source = "bmi", target = "bmi")
	MeasurementResponse toMeasurementResponse(Measurement measurement);

}
