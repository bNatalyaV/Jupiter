package id.bnv.jupiter.exception;

import id.bnv.jupiter.interceptor.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(Interceptor.class);


    @ExceptionHandler(UserException.class)
    public ResponseEntity handleUserExeption (UserException exception) {
        logger.info("UserException", exception);
        return ResponseEntity.status(405).body(exception);
    }

    @ExceptionHandler(NumberException.class)
    public ResponseEntity handleNumberException(NumberException exception) {
        logger.info("NumberException", exception);
        return ResponseEntity.status(405).body(exception);
    }
}
