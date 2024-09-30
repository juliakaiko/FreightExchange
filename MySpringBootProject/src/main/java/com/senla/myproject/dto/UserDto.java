package com.senla.myproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String password;
    private String firstName;
    private String surName;
}
