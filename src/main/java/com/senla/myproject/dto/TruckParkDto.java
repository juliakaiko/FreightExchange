package com.senla.myproject.dto;

import com.senla.myproject.model.Carrier;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TruckParkDto {

    private Long id;

    @NotNull(message = "Trucks' number may not be null")
    private Integer trucksNum;

    @NotNull(message = "Trucks' load capacity may not be null")
    private Integer trucksLoadCapacity;

    private Carrier carrier;
}
