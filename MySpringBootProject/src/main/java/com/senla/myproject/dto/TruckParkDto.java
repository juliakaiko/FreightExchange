package com.senla.myproject.dto;

import com.senla.myproject.model.Carrier;
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
    private Integer trucksNum;
    private Integer trucksLoadCapacity;
    private Carrier carrier;
}
