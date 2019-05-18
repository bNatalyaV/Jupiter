package id.bnv.jupiter.Exeption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.Serializable;

@ControllerAdvice
public class ExeptionHandlerController implements Serializable {

    @ExceptionHandler(UserExeptions.class)
    public ResponseEntity getUserExeption (UserExeptions exeption) {
        String message=exeption.getMessage();

    }
}
