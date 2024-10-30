package com.senla.myproject.advices;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.exceptions.ErrorItem;
import com.senla.myproject.exceptions.NotFoundException;
import com.senla.myproject.exceptions.OrderNotValidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@RestControllerAdvice(annotations = UserExceptionHandler.class)
public class UserAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity handleNotFoundException(NotFoundException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleProgrammEntityNotFoundException(EntityNotFoundException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity handleNoSuchElementException(NoSuchElementException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler({OrderNotValidException.class})
    public ResponseEntity handleOrderNotValidException(OrderNotValidException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleValidationException(ConstraintViolationException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity handleAuthorizationDeniedException(AuthorizationDeniedException e) { //<ErrorItem>
        ErrorItem error = generateMessage(e);
        return ResponseEntity.ok(error);
    }

    public ErrorItem generateMessage(Exception e){
        ErrorItem error = new ErrorItem();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), e.getMessage());
        error.setMessage(message);
        return error;
    }
}