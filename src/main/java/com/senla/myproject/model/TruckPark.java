package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "truck_park")
@Entity (name = "TruckPark")
public class TruckPark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column (name="trucks_num")
    private Integer trucksNum;

    @Column (name="trucks_load_capacity")
    private Integer trucksLoadCapacity;

    // one TruckParks for one Carrier=> @OneToOne
    @OneToOne (cascade=CascadeType.ALL)
    @MapsId
    @JsonIgnore
    private Carrier carrier;

    @PreRemove
    private void preRemove() {
        if (this.carrier != null)
            carrier.setPark(null);
    }
}
