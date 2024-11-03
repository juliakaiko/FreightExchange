package com.senla.myproject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorItem {

    private int status;
    private String message;
    private String timestamp;

}
