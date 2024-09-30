package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarriageRequest;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Builder
public class FreightForwarderDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String surName;
    @JsonIgnore
    private Set<CarriageRequest> orders;

}
