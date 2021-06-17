package vs.dietlogsrev.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.service.MeasurementService;

@RestController
@RequestMapping("measurements")
public class MeasurementsController {
    
    private final MeasurementService measurementService;
    
    public MeasurementsController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }
    
    @PostMapping("{userId}")
    public void add(@PathVariable("userId") int userId, @Valid @RequestBody CreateMeasurementRequest request) {
        this.measurementService.add(userId, request);
    }

}
