package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Table(name = "freight_forwarder") // говорим Hibernate, с какой именно таблицей необходимо связать (map) данный класс.
@Entity (name = "FreightForwarder") // на этот объект будет мапиться SQL
public class FreightForwarder extends User {

    //у одного экспедитора, м.б. много заказов CascadeType.PERSIST
    @OneToMany(mappedBy="forwarder", cascade = CascadeType.MERGE, fetch= FetchType.LAZY, orphanRemoval = false) //fetch=FetchType.LAZY, orphanRemoval = true
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();

    @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setForwarder(null));
    }
}
