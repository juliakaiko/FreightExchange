package com.senla.myproject.repository;

import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreightForwarderRepository extends JpaRepository<FreightForwarder, Long> {

    @Override
    FreightForwarder getOne(Long aLong);

    @Override
    void deleteById(Long aLong);
}
