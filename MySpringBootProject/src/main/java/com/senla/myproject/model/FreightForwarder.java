package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Table(name = "freight_forwarder") // говорим Hibernate, с какой именно таблицей необходимо связать (map) данный класс.
@Entity (name = "FreightForwarder") // на этот объект будет мапиться SQL
public class FreightForwarder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД вставляет данные и она автоматически присваивает значение
    @Column(name="id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="firstname")
    private String firstName;

    @Column(name="surname")
    private String surName;

    //у одного экспедитора, м.б. много заказов CascadeType.PERSIST
    @OneToMany(mappedBy="forwarder", cascade = CascadeType.PERSIST, fetch= FetchType.LAZY, orphanRemoval = false) //fetch=FetchType.LAZY, orphanRemoval = true
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();

    @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setForwarder(null));
    }
}
