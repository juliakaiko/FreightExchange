package com.senla.myproject.util;

import com.senla.myproject.model.User;
import lombok.experimental.UtilityClass;

import static com.senla.myproject.model.Role.MANAGER;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class UserGenerator {
    public static User generateUser() {
        return User.builder()
                .id(1l)
                .firstName("UserFirstName")
                .surName("UserSurName")
                .email("user@test.by")
                .password("userpass")
                .role(MANAGER)
                .build();
    }
}
