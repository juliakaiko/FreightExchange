package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"carrierManagers", "park"})
@ToString(exclude = {"carrierManagers", "park"})
@Table(name = "carrier")
@Entity (name = "Carrier")
public class Carrier  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column (name="name")
    private String name;

    @Column (name="address")
    private String address;

    // a Carrier may have many CarrierManagers, and a CarrierManager may work for several carrier companies
    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {
                    CascadeType.MERGE,
            })
    @JoinTable(
            name = "carrier_managers",
            joinColumns = { @JoinColumn(name = "carrier_id") },
            inverseJoinColumns = { @JoinColumn(name = "manager_id") }
    )
    @JsonIgnore
    private Set<CarrierManager> carrierManagers= new HashSet<>();

    /// The carrier may have only one TruckPark and vice versa
    @OneToOne(mappedBy = "carrier", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private TruckPark park;
}
