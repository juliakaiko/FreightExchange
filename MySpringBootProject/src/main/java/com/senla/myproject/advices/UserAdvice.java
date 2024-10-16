package com.senla.myproject.advices;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.exceptions.ErrorItem;
import com.senla.myproject.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@RestControllerAdvice (annotations = UserExceptionHandler.class)
public class UserAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorItem> handleMyException(NotFoundException e) {
        ErrorItem error = new ErrorItem();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), e.getMessage());
        error.setMessage(message);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorItem> handleProgrammException(EntityNotFoundException e) {
        ErrorItem error = new ErrorItem();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), e.getMessage());
        error.setMessage(message);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}