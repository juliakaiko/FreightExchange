package com.senla.myproject.advices;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.exceptions.ErrorItem;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice (annotations = UserExceptionHandler.class)
public class UserAdvice {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorItem> handle(EntityNotFoundException e) {
        ErrorItem error = new ErrorItem();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), e.getMessage());
        error.setMessage(message);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}