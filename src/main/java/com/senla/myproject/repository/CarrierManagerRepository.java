package com.senla.myproject.repository;

import com.senla.myproject.model.CarrierManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrierManagerRepository extends JpaRepository<CarrierManager, Long> {

    @EntityGraph(value = "carrier_manager_entity_graph")
    Optional <CarrierManager> findCarrierManagerWithEntityGraphByEmail(String email);

    Optional <CarrierManager> findCarrierManagerByEmailIsLike(String email);

    @Query(value = "select * from carrier_manager", nativeQuery = true)
    Page<CarrierManager> findAllManagersNative(Pageable pageable);
}