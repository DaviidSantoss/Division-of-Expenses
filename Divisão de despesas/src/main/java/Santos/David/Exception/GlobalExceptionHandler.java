package Santos.David.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleGroupAlreadyExists(GroupAlreadyExistsException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(GroupRequiredException.class)
    public ResponseEntity<ErrorResponse> handleGroupRequiredException(Exception exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }



}
