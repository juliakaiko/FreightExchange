package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"forwarder", "manager"})
@ToString(exclude = {"forwarder", "manager"})
@Builder
public class CarriageRequestDto {
    private Long id;
    private String orderName;
    private String startPoint;
    private String finishPoint;
    private String cargo;
    private Long freight;
    private Boolean valid;
    @JsonIgnore
    private FreightForwarder forwarder;
    @JsonIgnore
    private CarrierManager manager;
}
