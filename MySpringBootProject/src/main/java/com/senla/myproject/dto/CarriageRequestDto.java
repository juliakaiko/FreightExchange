package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "OrderName may not be empty")
    private String orderName;

    @NotBlank(message = "StartPoint may not be empty")
    private String startPoint;

    @NotBlank(message = "FinishPoint may not be empty")
    private String finishPoint;

    @NotBlank(message = "Cargo may not be empty")
    private String cargo;

    @NotNull(message = "Freight may not be null")
    @Min (value = 10, message = "Transportation freight cannot be so low")
    private Long freight;

    @NotNull(message = "Valid status may not be null")
    private Boolean valid;

    @JsonIgnore
    private FreightForwarder forwarder;

    @JsonIgnore
    private CarrierManager manager;
}
