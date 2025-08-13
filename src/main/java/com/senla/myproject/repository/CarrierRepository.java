package com.senla.myproject.repository;

import com.senla.myproject.model.Carrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {

    @Query(value = "select * from carrier", nativeQuery = true)
    Page<Carrier> findAllCarriersNative(Pageable pageable);
}
