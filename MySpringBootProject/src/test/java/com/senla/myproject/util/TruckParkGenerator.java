package com.senla.myproject.util;

import com.senla.myproject.model.TruckPark;
import lombok.experimental.UtilityClass;

@UtilityClass //делает класс статическим и запрещает его наследование.
public class TruckParkGenerator {
    public static TruckPark generateTruckPark() {
        return TruckPark.builder()
                .id(1l)
                .trucksNum(500)
                .trucksLoadCapacity(10)
                .build();
    }
}
