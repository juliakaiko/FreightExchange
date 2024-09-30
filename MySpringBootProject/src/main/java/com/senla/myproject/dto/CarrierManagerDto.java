package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.model.Carrier;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"carriers", "orders"})
@ToString(exclude = {"carriers", "orders"})
@Builder
public class CarrierManagerDto {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String surName;
    @JsonIgnore
    private Set<Carrier> carriers; // = new HashSet<>();;
    @JsonIgnore
    private Set<CarriageRequest> orders;// = new HashSet<>();
}
