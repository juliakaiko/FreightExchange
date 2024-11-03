package com.senla.myproject.service;

import com.senla.myproject.dto.*;
import com.senla.myproject.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FreightExchangeService {

    CarrierManagerDto findCarrierManagerById(Long id);
    List<CarrierManager> findAllCarrierManagers();
    CarrierManagerDto saveCarrierManager(CarrierManagerDto userDTO);
    CarrierManagerDto deleteCarrierManagerById(Long id);
    CarrierManagerDto findCarrierManagerByEmailIsLike(String email);
    //EntityGraph
    CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email);

    Page<CarrierManagerDto> findAllManagersNativeWithPagination(Integer page, Integer size);

    FreightForwarderDto findFreightForwarderById(Long id);
    FreightForwarderDto findFreightForwarderByEmailIsLike (String email);
    FreightForwarderDto saveFreightForwarder(FreightForwarderDto userDTO);
    FreightForwarderDto deleteFreightForwarderById(Long id);
    List <FreightForwarder> findAllForwarders();
    Page<FreightForwarderDto> findAllForwardersNativeWithPagination(Integer page, Integer size);

    CarrierDto findCarrierById(Long id);
    CarrierDto saveCarrier(CarrierDto carrierDTO);
    CarrierDto deleteCarrierById(Long id);
    List <Carrier> findAllCarriers();
    Page<CarrierDto> findAllCarriersNativeWithPagination(Integer page, Integer size);

    CarriageRequestDto findOrderById(Long id);
    CarriageRequestDto findOrderByName(String orderName);
    CarriageRequestDto takeValidOrder(CarrierManager manager, String orderName);
    CarriageRequestDto cancelOrder(CarrierManager manager, String orderName);
    CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO);
    CarriageRequestDto deleteOrderById(Long id);
    List<CarriageRequest> findAllOrders();
    Page<CarriageRequestDto> findAllOrdersNativeWithPagination(Integer page, Integer size);
    List<CarriageRequest> findAllFreeOrders();

    TruckParkDto findTruckParkById(Long id);
    TruckParkDto saveTruckPark(TruckParkDto parkDTO);
    TruckParkDto deleteTruckParkById(Long id);
    List <TruckPark> findAllTruckParks();
    Page<TruckParkDto> findAllTruckParksNativeWithPagination(Integer page, Integer size);
}
