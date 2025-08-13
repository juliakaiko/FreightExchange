package com.senla.myproject.util;

import com.senla.myproject.model.Carrier;
import lombok.experimental.UtilityClass;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class CarrierGenerator {
    public static Carrier generateCarrier() {
        return Carrier.builder()
                .id(1L)
                .name("Carrier's name")
                .address("Carrier's address")
                .build();
    }
}
