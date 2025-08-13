package com.senla.myproject.exceptions;

public class OrderNotValidException  extends RuntimeException{

    public OrderNotValidException (String message) {
        super(message);
    }
}
