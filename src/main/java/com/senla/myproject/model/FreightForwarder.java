package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Table(name = "freight_forwarder")
@Entity (name = "FreightForwarder")
public class FreightForwarder extends User {

    //one FreightForwarder may have many CarriageRequests
    @OneToMany(mappedBy="forwarder", cascade = CascadeType.MERGE, fetch= FetchType.LAZY, orphanRemoval = false)
    @JsonIgnore
    private Set<CarriageRequest> orders = new HashSet<>();

    @PreRemove
    private void preRemove() {
        if (this.orders != null)
            orders.forEach(order -> order.setForwarder(null));
    }
}
