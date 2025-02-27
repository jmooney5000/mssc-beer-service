package guru.springframework.msscbeerservice.web.controller.v2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> validationErrorHandler(MethodArgumentNotValidException e) {


        List<String> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getBindingResult().getAllErrors().stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toList());

        fieldErrors.forEach(fieldError -> {

            errors.add( String.format("Bad Request %s : %s : Rejected value : ---> %s"
                    ,fieldError.getField()
                    ,fieldError.getDefaultMessage()
                    ,fieldError.getRejectedValue()));
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
