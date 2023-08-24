package org.kucher.socialservice.exception;

import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.dto.message.MultipleMessage;
import org.kucher.socialservice.exception.crud.api.CrudException;
import org.kucher.socialservice.exception.rest.RestApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RestApiException.class})
    public ResponseEntity<Object> handleInternalServerError(RuntimeException ex) {
        Message error = new Message("error", "Something was wrong. Try again later");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({EntityNotFoundException.class, AccessDeniedException.class, IllegalArgumentException.class, CrudException.class})
    public ResponseEntity<Object> handleCrudException(RuntimeException ex) {
        Message error = new Message("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MultipleMessage errors = new MultipleMessage("error");
        List<Message> listErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error)
                -> listErrors.add(new Message(((FieldError) error).getField(), error.getDefaultMessage())));

        errors.setMessages(listErrors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
