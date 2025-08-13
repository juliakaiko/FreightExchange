package com.senla.myproject.util;

import com.senla.myproject.model.CarriageRequest;
import lombok.experimental.UtilityClass;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class CarriageRequestGenerator {
    public static CarriageRequest generateOrder() {
        return CarriageRequest.builder()
                .id(1l)
                .orderName("TestOrderName")
                .cargo("TestCargo")
                .freight(55555L)
                .startPoint("A")
                .finishPoint("B")
                .valid(true)
                .build();
    }
}
