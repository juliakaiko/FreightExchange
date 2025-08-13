package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {"carriers", "orders"})
@ToString(exclude = {"carriers", "orders"})
@Table(name = "carrier_manager")
@Entity (name = "CarrierManager")
@NamedEntityGraph (name = "carrier_manager_entity_graph",
                   attributeNodes = @NamedAttributeNode("carriers"))
public class CarrierManager extends User  {

     @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
     @JoinTable(
             name = "carrier_managers",
             joinColumns = { @JoinColumn(name = "manager_id") },
             inverseJoinColumns = { @JoinColumn(name = "carrier_id") }
     )
     @JsonIgnore
     private Set<Carrier> carriers = new HashSet<>();

    //one CarrierManager may have many CarriageRequests
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY,cascade = CascadeType.MERGE, orphanRemoval = false) // cascade = CascadeType.ALL
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();

    @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setManager(null));
    }
}