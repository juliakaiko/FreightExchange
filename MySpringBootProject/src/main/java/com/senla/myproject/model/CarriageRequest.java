package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"forwarder", "manager"})
@ToString(exclude = {"forwarder", "manager"})
@Table(name = "carriage_request") // говорим Hibernate, с какой именно таблицей необходимо связать (map) данный класс.
@Entity(name = "CarriageRequest")// на этот объект будет мапиться SQL
public class CarriageRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД вставляет данные и она автоматически присваивает значение
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

    //много заказов м.б. у одного экспедитора => @ManyToOne
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private FreightForwarder forwarder;

    //много заказов м.б. у одного логиста => @ManyToOne // CascadeType.PERSIST
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private CarrierManager manager;

    @PreRemove
    private void preRemove() {
        if (this.manager != null)
            manager.getOrders().remove(this); //forEach(order -> order.setManager(null));
        if (this.forwarder != null)
            forwarder.getOrders().remove(this);
    }

}
