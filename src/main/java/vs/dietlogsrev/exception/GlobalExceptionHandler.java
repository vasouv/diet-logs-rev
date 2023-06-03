package vs.dietlogsrev.exception;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // for the @Valid annotation in request body
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Get all errors
        var errorList = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).toList();
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), errorList));
    }

    // for the @Validated annotation in path parameters
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEntityExists(EntityExistsException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()));
    }

    // for the unique parameter in jpa entity
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ErrorMessage.EMAIL_EXISTS));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(ErrorMessage.USER_NOT_FOUND));
    }

    @ExceptionHandler(UserInfoNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserInfoNotFoundException(UserInfoNotFoundException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(ErrorMessage.USER_INFO_NOT_FOUND));
    }

    @ExceptionHandler(MeasurementDateInFutureException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMeasurementDateInFutureException(MeasurementDateInFutureException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ErrorMessage.MEASUREMENT_DATE_IN_FUTURE));
    }

    @ExceptionHandler(MeasurementWeightNegativeOrZero.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMeasurementWeightNegativeOrZeroException(MeasurementWeightNegativeOrZero ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ErrorMessage.MEASUREMENT_WEIGHT_NEGATIVE_OR_ZERO));
    }

    @ExceptionHandler(AppointmentDateInFutureException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAppointmentDateInFutureException(AppointmentDateInFutureException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ErrorMessage.APPOINTMENT_DATE_IN_FUTURE));
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
