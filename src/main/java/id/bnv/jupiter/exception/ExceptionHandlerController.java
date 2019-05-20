package id.bnv.jupiter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity handleUserExeption (UserException exception) {
        return ResponseEntity.status(405).body(exception);
    }

    @ExceptionHandler(NumberException.class)
    public ResponseEntity handleNumberException(NumberException exception) {
        return ResponseEntity.status(405).body(exception);
    }
}
