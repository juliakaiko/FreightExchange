package com.senla.myproject.exceptions;

public class ErrorItem {
    private String message;

    public ErrorItem() {
    }

    public ErrorItem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
