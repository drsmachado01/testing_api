package br.com.axe.api.resources.exceptions;

import br.com.axe.api.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(
            ObjectNotFoundException ex,
            HttpServletRequest req
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardError.builder().
                        timestamp(LocalDateTime.now()).
                        status(HttpStatus.NOT_FOUND.value()).
                        error(ex.getMessage()).
                        path(req.getRequestURI()).
                        build()
        );
    }
}
