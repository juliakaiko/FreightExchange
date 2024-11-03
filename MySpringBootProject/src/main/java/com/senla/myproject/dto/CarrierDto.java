package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.TruckPark;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"carrierManagers", "park"})
@ToString(exclude = {"carrierManagers", "park"})
@Builder
public class CarrierDto {

    private Long id;

    @NotBlank(message = "Carrier name may not be empty")
    private String name;

    @NotBlank(message = "Carrier address may not be empty")
    private String address;

    private TruckPark park;

    @JsonIgnore
    private Set<CarrierManager> carrierManagers;

}
