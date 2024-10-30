package com.senla.myproject.repository;

import com.senla.myproject.model.TruckPark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckParkRepository extends JpaRepository<TruckPark, Long> {

    @Query(value = "select * from truck_park", nativeQuery = true) //nativeQuery = true - SQL
    Page<TruckPark> findAllTruckParksNative(Pageable pageable);
}
