package Santos.David.Exception;

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


    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotFoundException(GroupNotFoundException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(UserAlreadyInGroupException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyInGroupException(UserAlreadyInGroupException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(UserDisableExcpetion.class)
    public ResponseEntity<ErrorResponse> handleUserDisableExcpetion(UserDisableExcpetion exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(EmptyGroupException.class)
    public ResponseEntity<ErrorResponse> handleEmptyGroupException(EmptyGroupException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NO_CONTENT.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


    @ExceptionHandler(BillSplitAmountMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBillSplitAmountMismatchException(BillSplitAmountMismatchException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(RecordsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordsNotFoundException(RecordsNotFoundException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpenseNotFoundException(ExpenseNotFoundException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(ExpenseValueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpenseValueNotFoundException(ExpenseValueNotFoundException exception){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
