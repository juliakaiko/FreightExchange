package com.senla.myproject.repository;

import com.senla.myproject.model.CarrierManager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrierManagerRepository extends JpaRepository<CarrierManager, Long> {

   /* @Override
    CarrierManager getOne(Long aLong);

    @Override
    void deleteById(Long aLong);
*/
   //EntityGraph
    @EntityGraph(value = "carrier_manager_entity_graph")
    CarrierManager findCarrierManagerWithEntityGraphByEmail(String email);


}