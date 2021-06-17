package vs.dietlogsrev.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // for the @Valid annotation in request body
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        // Get all errors
        var errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), errorList));

    }

    // for the @Validated annotation in path parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExists(EntityExistsException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage())));
    }


    // for the unique parameter in jpa entity
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),List.of(ErrorMessage.EMAIL_EXISTS)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(ErrorMessage.USER_NOT_FOUND)));
    }

    @ExceptionHandler(MeasurementDateInFutureException.class)
    public ResponseEntity<ErrorResponse> handleMeasurementDateInFutureException(MeasurementDateInFutureException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),List.of(ErrorMessage.MEASUREMENT_DATE_IN_FUTURE)));
    }
    
    @ExceptionHandler(MeasurementWeightNegativeOrZero.class)
    public ResponseEntity<ErrorResponse> handleMeasurementWeightNegativeOrZeroException(MeasurementWeightNegativeOrZero ex, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ErrorMessage.MEASUREMENT_WEIGHT_NEGATIVE_OR_ZERO)));
    }

    private class ErrorResponse {
        public final int status;
        public final List<String> errors;

        public ErrorResponse(int status, List<String> errors) {
            this.status = status;
            this.errors = errors;
        }
    }
}
