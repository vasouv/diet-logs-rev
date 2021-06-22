package vs.dietlogsrev.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vs.dietlogsrev.model.CreateMeasurementRequest;
import vs.dietlogsrev.model.MeasurementResponse;
import vs.dietlogsrev.service.MeasurementService;

@RestController
@RequestMapping("measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;

    public MeasurementsController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable("userId") int userId, @Valid @RequestBody CreateMeasurementRequest request) {
        this.measurementService.add(userId, request);
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<MeasurementResponse>> findByUserId(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(this.measurementService.findByUserId(userId));
    }

}
