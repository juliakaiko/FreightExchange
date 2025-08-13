package com.senla.myproject.repository;

import com.senla.myproject.model.FreightForwarder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreightForwarderRepository extends JpaRepository<FreightForwarder, Long> {

    Optional <FreightForwarder> findFreightForwarderByEmailIsLike (String email);

    @Query(value = "select * from freight_forwarder", nativeQuery = true)
    Page<FreightForwarder> findAllFreightForwardersNative(Pageable pageable);
}
