package soul.dev.productservice.common.errorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception){
        return new ResponseEntity<String>(exception.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
}
