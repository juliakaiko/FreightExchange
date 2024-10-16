package com.senla.myproject.repository;

import com.senla.myproject.model.Carrier;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreightForwarderRepository extends JpaRepository<FreightForwarder, Long> {

    @Query(value = "select * from freight_forwarder", nativeQuery = true) //nativeQuery = true - SQL
    Page<FreightForwarder> findAllFreightForwardersNative(Pageable pageable);
}
