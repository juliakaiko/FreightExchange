package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {"carriers", "orders"})
@ToString(exclude = {"carriers", "orders"})
@Table(name = "carrier_manager") // С помощью этой аннотации мы говорим Hibernate,  с какой именно таблицей необходимо связать (map) данный класс.
@Entity (name = "CarrierManager") // на этот объект будет мапиться SQL
@NamedEntityGraph (name = "carrier_manager_entity_graph", //загружает данные в один запрос выбора, избегая повторного обращения к базе данных
                   attributeNodes = @NamedAttributeNode("carriers"))
public class CarrierManager extends User implements Serializable { // UserDetails

     @ManyToMany(fetch = FetchType.LAZY, cascade =
             {
                     //CascadeType.DETACH,
                     CascadeType.MERGE,
                     //CascadeType.REFRESH,
                     CascadeType.PERSIST
             })
     @JoinTable(
             name = "carrier_managers",
             joinColumns = { @JoinColumn(name = "manager_id") },
             inverseJoinColumns = { @JoinColumn(name = "carrier_id") }
     )
     @JsonIgnore
     private Set<Carrier> carriers = new HashSet<>();

    //у одного логиста, м.б. много заказов //CascadeType.PERSIST
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY,cascade = CascadeType.MERGE, orphanRemoval = false) // cascade = CascadeType.ALL
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();

    @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setManager(null));
    }
}