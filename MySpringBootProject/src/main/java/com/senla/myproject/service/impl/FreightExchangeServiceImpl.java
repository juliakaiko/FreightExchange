package com.senla.myproject.service.impl;

import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.mapper.*;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreightExchangeServiceImpl implements FreightExchangeService {

    //private final EntityMapper entityMapper;
    private final CarrierRepository carrierRepository;
    private final CarrierManagerRepository managerRepository;
    private final FreightForwarderRepository forwarderRepository;
    private final CarriageRequestRepository orderRepository;
    private final TruckParkRepository parkRepository;

    //CarrierManager
    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerById(Long id) {
        //CarrierManager manager = managerRepository.getOne(id);
        Optional<CarrierManager> manager = managerRepository.findById(id);
        log.info("FROM SERVISE: findCarrierManagerById() => manager: "+manager);
        //return entityMapper.managerToDTO(manager);
        return CarrierManagerMapper.INSTANSE.toDTO(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email){
        CarrierManager manager = managerRepository.findCarrierManagerWithEntityGraphByEmail(email);
        log.info("FROM SERVISE: findCarrierManagerWithEntityGraphByEmail() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDTO(manager);
        //return entityMapper.managerToDTO(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierManager> findAllCarrierManagers() {
        return managerRepository.findAll();
    }

    @Override
    @Transactional
    public CarrierManagerDto saveCarrierManager(CarrierManagerDto managerDto){
        CarrierManager manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);//entityMapper.managerDtoToEntity(managerDto);
        //CarrierManager manager = entityMapper.managerDtoToEntity(managerDto);
        managerRepository.save(manager);
        return managerDto;
    }

    @Override
    @Transactional
    public CarrierManagerDto deleteCarrierManagerById(Long id) {
        //CarrierManager manager = managerRepository.getOne(id);
        Optional<CarrierManager> manager = managerRepository.findById(id);
        managerRepository.deleteById(id);
        log.info("FROM SERVISE: deleteCarrierManagerById() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDTO(manager.get());
        //return entityMapper.managerToDTO(manager);
    }

    //Carrier
    @Override
    @Transactional(readOnly = true)
    public CarrierDto findCarrierById(Long id) {
        //Carrier carrier = carrierRepository.getOne(id);
        Optional<Carrier> carrier = carrierRepository.findById(id);
        return CarrierMapper.INSTANSE.toDTO(carrier.get());
        //return entityMapper.carrierToDTO(carrier) ;
    }

    @Override
    @Transactional
    public CarrierDto saveCarrier(CarrierDto carrierDTO){
        Carrier carrier = CarrierMapper.INSTANSE.toEntity(carrierDTO);//entityMapper.carrierDtoToEntity(carrierDTO);
        //Carrier carrier = entityMapper.carrierDtoToEntity(carrierDTO);
        carrierRepository.save(carrier);
        return carrierDTO;
    }

    @Override
    @Transactional
    public CarrierDto deleteCarrierById(Long id) {
        //Carrier carrier = carrierRepository.getOne(id);
        Optional<Carrier> carrier = carrierRepository.findById(id);
        carrierRepository.deleteById(id);
        return CarrierMapper.INSTANSE.toDTO(carrier.get());
        //return entityMapper.carrierToDTO(carrier);
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
        //FreightForwarder forwarder = forwarderRepository.getOne(id);
        Optional<FreightForwarder> forwarder = forwarderRepository.findById(id);
        return FreightForwarderMapper.INSTANSE.toDTO(forwarder.get());
        //return entityMapper.forwarderToDTO(forwarder);
    }

    @Override
    @Transactional
    public FreightForwarderDto saveFreightForwarder(FreightForwarderDto forwarderDto){
        FreightForwarder forwarder = FreightForwarderMapper.INSTANSE.toEntity(forwarderDto);//entityMapper.forwarderDtoToEntity(userDTO);
        //FreightForwarder forwarder = entityMapper.forwarderDtoToEntity(forwarderDto);
        forwarderRepository.save(forwarder);
        return forwarderDto;
    }

    @Override
    @Transactional
    public FreightForwarderDto deleteFreightForwarderById(Long id) {
        //FreightForwarder forwarder = forwarderRepository.getOne(id);
        Optional<FreightForwarder> forwarder = forwarderRepository.findById(id);
        forwarderRepository.deleteById(id);
        return FreightForwarderMapper.INSTANSE.toDTO(forwarder.get());
        //return entityMapper.forwarderToDTO(forwarder);
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
        //CarriageRequest order = orderRepository.getOne(id);
        Optional<CarriageRequest> order = orderRepository.findById(id);
        return CarriageRequestMapper.INSTANSE.toDTO(order.get());
        //return entityMapper.carriageRequestToDTO(order);
    }

    @Override
    @Transactional
    public CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO){
        CarriageRequest order = CarriageRequestMapper.INSTANSE.toEntity(carriageRequestDTO);//entityMapper.carriageRequestDtoToEntity(carriageRequestDTO);
        //CarriageRequest order = entityMapper.carriageRequestDtoToEntity(carriageRequestDTO);
        orderRepository.save(order);
        return carriageRequestDTO;
    }

    @Override
    @Transactional
    public CarriageRequestDto deleteOrderById(Long id) {
        //CarriageRequest order = orderRepository.getOne(id);
        Optional<CarriageRequest> order = orderRepository.findById(id);
        orderRepository.deleteById(id);
        return CarriageRequestMapper.INSTANSE.toDTO(order.get());
        //return entityMapper.carriageRequestToDTO(order);
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
        //TruckPark park = parkRepository.getOne(id);
        Optional<TruckPark> park = parkRepository.findById(id);
        return TruckParkMapper.INSTANSE.toDTO(park.get());
        //return entityMapper.truckParkToDTO(park);
    }

    @Override
    @Transactional
    public TruckParkDto saveTruckPark(TruckParkDto parkDTO){
        TruckPark park = TruckParkMapper.INSTANSE.toEntity(parkDTO);//entityMapper.truckParkDtoToEntity(parkDTO);
        //TruckPark park = entityMapper.truckParkDtoToEntity(parkDTO);
        parkRepository.save(park);
        return parkDTO;
    }

    @Override
    @Transactional
    public TruckParkDto deleteTruckParkById(Long id) {
        //TruckPark park = parkRepository.getOne(id);
        Optional<TruckPark> park = parkRepository.findById(id);
        parkRepository.deleteById(id);
        return TruckParkMapper.INSTANSE.toDTO(park.get());
        //return entityMapper.truckParkToDTO(park);
    }

    @Transactional(readOnly = true)
    public List<TruckPark> findAllTruckParks(){
        return parkRepository.findAll();
    }
}
