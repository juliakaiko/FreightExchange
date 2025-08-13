package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"forwarder", "manager"})
@ToString(exclude = {"forwarder", "manager"})
@Table(name = "carriage_request")
@Entity(name = "CarriageRequest")
public class CarriageRequest  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="order_name")
    private String orderName;

    @Column(name="start_point")
    private String startPoint;

    @Column(name="finish_point")
    private String finishPoint;

    @Column(name="cargo")
    private String cargo;

    @Column(name="freight")
    private Long freight;

    @Column(name="valid")
    @ColumnDefault("true")
    private Boolean valid;

    //many CarriageRequests may be from one FreightForwarder => @ManyToOne
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private FreightForwarder forwarder;

    //many CarriageRequests can be from one CarrierManager => @ManyToOne
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private CarrierManager manager;

    @PreRemove
    private void preRemove() {
        if (this.manager != null)
            manager.getOrders().remove(this);
        if (this.forwarder != null)
            forwarder.getOrders().remove(this);
    }

}
