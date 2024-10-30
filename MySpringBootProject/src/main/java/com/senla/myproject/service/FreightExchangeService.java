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

    Page<CarrierManagerDto> findAllManagersNativeWithPagination(int page, int size);

    FreightForwarderDto findFreightForwarderById(Long id);
    FreightForwarderDto findFreightForwarderByEmailIsLike (String email);
    FreightForwarderDto saveFreightForwarder(FreightForwarderDto userDTO);
    FreightForwarderDto deleteFreightForwarderById(Long id);
    List <FreightForwarder> findAllForwarders();
    Page<FreightForwarderDto> findAllForwardersNativeWithPagination(int page, int size);

    CarrierDto findCarrierById(Long id);
    CarrierDto saveCarrier(CarrierDto carrierDTO);
    CarrierDto deleteCarrierById(Long id);
    List <Carrier> findAllCarriers();
    Page<CarrierDto> findAllCarriersNativeWithPagination(int page, int size);

    CarriageRequestDto findOrderById(Long id);
    CarriageRequestDto findOrderByName(String orderName);
    CarriageRequestDto takeValidOrder(CarrierManager manager, String orderName);
    CarriageRequestDto cancelOrder(CarrierManager manager, String orderName);
    CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO);
    CarriageRequestDto deleteOrderById(Long id);
    List<CarriageRequest> findAllOrders();
    Page<CarriageRequestDto> findAllOrdersNativeWithPagination(int page, int size);
    List<CarriageRequest> findAllFreeOrders();

    TruckParkDto findTruckParkById(Long id);
    TruckParkDto saveTruckPark(TruckParkDto parkDTO);
    TruckParkDto deleteTruckParkById(Long id);
    List <TruckPark> findAllTruckParks();
    Page<TruckParkDto> findAllTruckParksNativeWithPagination(int page, int size);
}
