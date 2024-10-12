package com.senla.myproject.repository;

import com.senla.myproject.model.Carrier;
import com.senla.myproject.model.CarrierManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {

}
