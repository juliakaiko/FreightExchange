package com.senla.myproject.repository;

import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.TruckPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckParkRepository extends JpaRepository<TruckPark, Long> {

    @Override
    TruckPark getOne(Long aLong);

    @Override
    void deleteById(Long aLong);
}
