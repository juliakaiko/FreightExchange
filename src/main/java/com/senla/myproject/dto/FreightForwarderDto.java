package com.senla.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.myproject.model.CarriageRequest;
import java.util.Set;

import com.senla.myproject.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Email(regexp="\\w+@\\w+\\.\\w+", message="Please provide a valid email address") //  .+@.+\..+
    @NotBlank(message = "Email address may not be empty")
    private String email;

    @NotBlank (message = "Password may not be empty")
    @Size(min=5, max=255, message = "Password size must be between 5 and 255")
    private String password;

    @NotBlank(message = "Firstname may not be empty")
    @Size(min=2, max=50, message = "Firstname size must be between 2 and 50")
    private String firstName;

    @NotBlank(message = "Surname may not be empty")
    @Size(min=2, max=50, message = "Surname size must be between 2 and 50")
    private String surName;

    private Role role;

    @JsonIgnore
    private Set<CarriageRequest> orders;

}
