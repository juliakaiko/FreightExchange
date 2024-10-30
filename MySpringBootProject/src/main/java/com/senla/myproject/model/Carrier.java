package com.senla.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"carrierManagers", "park"})
@ToString(exclude = {"carrierManagers", "park"})
@Table(name = "carrier") // С помощью этой аннотации мы говорим Hibernate,  с какой именно таблицей необходимо связать данную сущность.
@Entity (name = "Carrier") // на этот объект будет мапиться SQL
public class Carrier implements Serializable { //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД вставляет данные и она автоматически присваивает значение
    @Column(name="id")
    private Long id;

    @Column (name="name")
    @NotBlank (message = "Carrier name may not be empty")
    private String name;

    @Column (name="address")
    @NotBlank(message = "Carrier address may not be empty")
    private String address;

    // у перевозчика м.б. много экспедиторов, так и экспедитор может работать на несколько фирм
    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {
                    //CascadeType.DETACH,
                    CascadeType.MERGE,
                    //CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "carrier_managers",
            joinColumns = { @JoinColumn(name = "carrier_id") },
            inverseJoinColumns = { @JoinColumn(name = "manager_id") }
    )
    @JsonIgnore
    private Set<CarrierManager> carrierManagers= new HashSet<>();

    /// у перевозчика м.б. только один автопарк и наоборот
    @OneToOne(mappedBy = "carrier", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private TruckPark park;
}
