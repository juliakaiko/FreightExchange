package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "truck_park")
@Entity (name = "TruckPark")
public class TruckPark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД вставляет данные и она автоматически присваивает значение
    @Column(name="id")
    private Long id;

    @Column (name="trucks_num")
    private Integer trucksNum;

    @Column (name="trucks_load_capacity")
    private Integer trucksLoadCapacity;

    // один автопарк у одного перевозчика => @OneToOne
    @OneToOne //(fetch = FetchType.LAZY)
    @MapsId // Связывает идентификаторы с сущностью Carrier
    @JsonIgnore
    private Carrier carrier;

    @PreRemove
    private void preRemove() {
        if (this.carrier != null)
            carrier.setPark(null);
    }

/*
    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
        this.id = carrier.getId(); // Для отработки связи @OneToOne
    }*/
}
