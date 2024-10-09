package com.senla.myproject.service;


import com.senla.myproject.dto.*;
import com.senla.myproject.model.*;

import java.util.List;

public interface FreightExchangeService {

    CarrierManagerDto findCarrierManagerById(Long id);
    List<CarrierManager> findAllCarrierManagers();
    CarrierManagerDto saveCarrierManager(CarrierManagerDto userDTO);
    CarrierManagerDto deleteCarrierManagerById(Long id);
    //EntityGraph
    CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email);

/*    CarrierManagerDTO findCarrierManagerWithJoinFetchById(Long id);
    CarrierManagerDTO findCarrierManagerWithCriteriaById(Long id);
    CarrierManagerDTO findUserByEmail(String email);
    CarrierManagerDTO findCarrierManagerWithEntityGraphById(Long id);*/

    FreightForwarderDto findFreightForwarderById(Long id);
    FreightForwarderDto saveFreightForwarder(FreightForwarderDto userDTO);
    FreightForwarderDto deleteFreightForwarderById(Long id);
    List <FreightForwarder> findAllForwarders();

    CarrierDto findCarrierById(Long id);
    CarrierDto saveCarrier(CarrierDto carrierDTO);
    CarrierDto deleteCarrierById(Long id);
    List <Carrier> findAllCarriers();

    CarriageRequestDto findOrderById(Long id);
    CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO);
    CarriageRequestDto deleteOrderById(Long id);
    List<CarriageRequest> findAllOrders();

    TruckParkDto findTruckParkById(Long id);
    TruckParkDto saveTruckPark(TruckParkDto parkDTO);
    TruckParkDto deleteTruckParkById(Long id);
    List <TruckPark> findAllTruckParks();
}
