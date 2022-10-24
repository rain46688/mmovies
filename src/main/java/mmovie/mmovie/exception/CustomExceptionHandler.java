package mmovie.mmovie.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(Exception e){
        ErrorMessage msg = new ErrorMessage().builder()
                .statusCode(500)
                .timeStamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<ErrorMessage>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
