package com.senla.myproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД вставляет данные и она автоматически присваивает значение
    @Column(name="id")
    private Long id;

    @Column(name="email")
    @Email(regexp="\\w+@\\w+\\.\\w+", message="Please provide a valid email address") //  .+@.+\..+
    @NotBlank(message = "Email address may not be empty")
    private String email;

    @Column(name="password")
    @NotBlank (message = "Password may not be empty")
    @Size(min=5, max=255, message = "Password size must be between 5 and 255")
    private String password;

    @Column(name="firstname")
    @NotBlank(message = "Firstname may not be empty")
    @Size(min=2, max=50, message = "Firstname size must be between 2 and 50")
    private String firstName;

    @Column(name="surname")
    @NotBlank(message = "Surname may not be empty")
    @Size(min=2, max=50, message = "Surname size must be between 2 and 50")
    private String surName;

    @Enumerated(EnumType.STRING) //в базе будет хранится имя этого enum
    @Column(name="role")
    private Role role;
}
