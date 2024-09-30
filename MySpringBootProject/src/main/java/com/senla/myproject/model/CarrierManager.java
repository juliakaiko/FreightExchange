package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"carriers", "orders"})
@ToString(exclude = {"carriers", "orders"})
@Table(name = "carriermanager") // С помощью этой аннотации мы говорим Hibernate,  с какой именно таблицей необходимо связать (map) данный класс.
@Entity (name = "CarrierManager") // на этот объект будет мапиться SQL
@NamedEntityGraph (name = "carrierManager_entity-graph", //загружает данные в один запрос выбора, избегая повторного обращения к базе данных
                   attributeNodes = @NamedAttributeNode("orders"))
public class CarrierManager implements Serializable {

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

     @ManyToMany(fetch = FetchType.LAZY, cascade =
             {
                     CascadeType.DETACH,
                     CascadeType.MERGE,
                     CascadeType.REFRESH,
                     CascadeType.PERSIST
             })
     @JoinTable(
             name = "carrier_managers",
             joinColumns = { @JoinColumn(name = "manager_id") },
             inverseJoinColumns = { @JoinColumn(name = "carrier_id") }
     )
     @JsonIgnore
     private Set<Carrier> carriers = new HashSet<>();

     //Из видео
     public void addCarrier (Carrier carrier){
        carriers.add(carrier);
        carrier.getCarrierManagers().add(this);
     }

     public void removeCarrier (Carrier carrier){
        carriers.remove(carrier);
        carrier.getCarrierManagers().remove(this);
     }

    //у одного логиста, м.б. много заказов // CascadeType.PERSIST
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = false) // cascade = CascadeType.ALL
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();


   /* @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setManager(null));
    }*/

}