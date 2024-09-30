package com.senla.myproject.util;

import com.senla.myproject.model.CarrierManager;
import lombok.experimental.UtilityClass;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class CarrierManagerGenerator {
    public static CarrierManager generateCarrierManager() {
        return CarrierManager.builder()
                .id(1l)
                .firstName("TestFirstName")
                .surName("TestSurName")
                .email("test@test.by")
                .password("test")
                .build();
    }
}
