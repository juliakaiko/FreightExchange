package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank (message = "Order name may not be empty")
    private String orderName;

    @Column(name="start_point")
    @NotBlank(message = "StartPoint may not be empty")
    private String startPoint;

    @Column(name="finish_point")
    @NotBlank(message = "FinishPoint may not be empty")
    private String finishPoint;

    @Column(name="cargo")
    @NotBlank(message = "Cargo may not be empty")
    private String cargo;

    @Column(name="freight")
    @NotNull(message = "Freight may not be null")
    private Long freight;

    @Column(name="valid")
    @ColumnDefault("true")
    private Boolean valid;

    //много заказов м.б. у одного экспедитора => @ManyToOne
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private FreightForwarder forwarder;

    //много заказов м.б. у одного логиста => @ManyToOne // CascadeType.PERSIST
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private CarrierManager manager;
}
