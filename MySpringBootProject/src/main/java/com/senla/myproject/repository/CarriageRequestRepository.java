package com.senla.myproject.repository;

import com.senla.myproject.model.CarriageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarriageRequestRepository extends JpaRepository<CarriageRequest, Long> {

    Optional <CarriageRequest> findByOrderNameIsLike(String orderName);

    //Collection, Stream, Streamable <- Slice <- Page
    @Query(value = "select * from carriage_request", nativeQuery = true) //nativeQuery = true - SQL
    Page<CarriageRequest> findAllOrdersNative(Pageable pageable);

}
