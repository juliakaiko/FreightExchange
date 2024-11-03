package com.senla.myproject.advices;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.exceptions.ErrorItem;
import com.senla.myproject.exceptions.NotFoundException;
import com.senla.myproject.exceptions.OrderNotValidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = UserExceptionHandler.class)
public class UserAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity <ErrorItem> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorItem error = new ErrorItem();
        error.setStatus(e.getStatusCode().value());
        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList())
                .toString();
        error.setMessage(errors);
        error.setTimestamp(formatDate());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity <ErrorItem> handleValidationException(ConstraintViolationException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity <ErrorItem> handleNotFoundException(NotFoundException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity <ErrorItem> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity <ErrorItem> handleNoSuchElementException(NoSuchElementException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OrderNotValidException.class})
    public ResponseEntity <ErrorItem> handleOrderNotValidException(OrderNotValidException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity <ErrorItem> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        ErrorItem error = generateMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public ErrorItem generateMessage(Exception e){
        ErrorItem error = new ErrorItem();
        error.setTimestamp(formatDate());
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return error;
    }

    public String formatDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String data = dateTimeFormatter.format( LocalDateTime.now() );
        return data;
    }
}