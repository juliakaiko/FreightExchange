package com.senla.myproject.service.impl;

import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.mapper.EntityMapper;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.repository.*;
import com.senla.myproject.model.*;
import com.senla.myproject.dto.*;
import com.senla.myproject.service.FreightExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreightExchangeServiceImpl implements FreightExchangeService {

    private final EntityMapper entityMapper;
    private final CarrierRepository carrierRepository;
    private final CarrierManagerRepository managerRepository;
    private final FreightForwarderRepository forwarderRepository;
    private final CarriageRequestRepository orderRepository;
    private final TruckParkRepository parkRepository;

    //CarrierManager
    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerById(Long id) {
        CarrierManager manager = managerRepository.getOne(id);
        log.info("FROM SERVISE: findCarrierManagerById() => manager: "+manager);
        return entityMapper.managerToDTO(manager);
        //return CarrierManagerMapper.INSTANSE.toDTO(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierManager> findAllCarrierManagers() {
        return managerRepository.findAll();
    }

    @Override
    @Transactional
    public CarrierManagerDto saveCarrierManager(CarrierManagerDto userDTO){
        CarrierManager manager = entityMapper.managerDtoToEntity(userDTO);
        managerRepository.save(manager);
        return userDTO;
    }

    @Override
    @Transactional
    public CarrierManagerDto deleteCarrierManagerById(Long id) {
        CarrierManager manager = managerRepository.getOne(id);
        managerRepository.deleteById(id);
        //managerRepository.delete(manager);
        log.info("FROM SERVISE: deleteCarrierManagerById() => manager: "+manager);
        return entityMapper.managerToDTO(manager);
    }

    //Carrier
    @Override
    @Transactional(readOnly = true)
    public CarrierDto findCarrierById(Long id) {
        Carrier carrier = carrierRepository.getOne(id);
        return entityMapper.carrierToDTO(carrier) ;
    }

    @Override
    @Transactional
    public CarrierDto saveCarrier(CarrierDto carrierDTO){
        Carrier carrier = entityMapper.carrierDtoToEntity(carrierDTO);
        carrierRepository.save(carrier);
        return carrierDTO;
    }

    @Override
    @Transactional
    public CarrierDto deleteCarrierById(Long id) {
        Carrier carrier = carrierRepository.getOne(id);
        carrierRepository.deleteById(id);
        return entityMapper.carrierToDTO(carrier);
    }

    @Override
    @Transactional(readOnly = true)
    public List <Carrier> findAllCarriers() {
        return carrierRepository.findAll();
    }

    //FreightForwarder
    @Override
    @Transactional(readOnly = true)
    public FreightForwarderDto findFreightForwarderById(Long id) {
        FreightForwarder forwarder = forwarderRepository.getOne(id);
        return entityMapper.forwarderToDTO(forwarder);
    }

    @Override
    @Transactional
    public FreightForwarderDto saveFreightForwarder(FreightForwarderDto userDTO){
        FreightForwarder forwarder = entityMapper.forwarderDtoToEntity(userDTO);
        forwarderRepository.save(forwarder);
        return userDTO;
    }

    @Override
    @Transactional
    public FreightForwarderDto deleteFreightForwarderById(Long id) {
        FreightForwarder forwarder = forwarderRepository.getOne(id);
        forwarderRepository.deleteById(id);
        return entityMapper.forwarderToDTO(forwarder);
    }

    @Override
    @Transactional(readOnly = true)
    public List <FreightForwarder> findAllForwarders() {
        return forwarderRepository.findAll();
    }

    //Order
    @Override
    @Transactional(readOnly = true)
    public CarriageRequestDto findOrderById(Long id) {
        CarriageRequest oder = orderRepository.getOne(id);
        return entityMapper.carriageRequestToDTO(oder);
    }

    @Override
    @Transactional
    public CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO){
        CarriageRequest order = entityMapper.carriageRequestDtoToEntity(carriageRequestDTO);
        orderRepository.save(order);
        return carriageRequestDTO;
    }

    @Override
    @Transactional
    public CarriageRequestDto deleteOrderById(Long id) {
        CarriageRequest order = orderRepository.getOne(id);
        orderRepository.deleteById(id);
        return entityMapper.carriageRequestToDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllOrders() {
        return orderRepository.findAll();
    }

    //TruckPark
    @Override
    @Transactional(readOnly = true)
    public TruckParkDto findTruckParkById(Long id) {
        TruckPark park = parkRepository.getOne(id);
        return entityMapper.truckParkToDTO(park);
    }

    @Override
    @Transactional
    public TruckParkDto saveTruckPark(TruckParkDto parkDTO){
        TruckPark park = entityMapper.truckParkDtoToEntity(parkDTO);
        parkRepository.save(park);
        return parkDTO;
    }

    @Override
    @Transactional
    public TruckParkDto deleteTruckParkById(Long id) {
        TruckPark park = parkRepository.getOne(id);
        parkRepository.deleteById(id);
        return entityMapper.truckParkToDTO(park);
    }

    @Transactional(readOnly = true)
    public List<TruckPark> findAllTruckParks(){
        return parkRepository.findAll();
    }
}
