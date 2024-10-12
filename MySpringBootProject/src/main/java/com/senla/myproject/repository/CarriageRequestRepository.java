package com.senla.myproject.repository;

import com.senla.myproject.model.CarriageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarriageRequestRepository extends JpaRepository<CarriageRequest, Long> {

}
