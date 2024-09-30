package com.senla.myproject.util;

import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import lombok.experimental.UtilityClass;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class FreightForwarderGenerator {
    public static FreightForwarder generateFreightForwarder() {
        return FreightForwarder.builder()
                .id(1l)
                .firstName("TestFirstName")
                .surName("TestSurName")
                .email("test@test.by")
                .password("test")
                .build();
    }
}
