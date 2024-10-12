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
        return CarrierManagerMapper.INSTANSE.toDTO(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email){
        CarrierManager manager = managerRepository.findCarrierManagerWithEntityGraphByEmail(email);
        log.info("FROM SERVISE: findCarrierManagerWithEntityGraphByEmail() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDTO(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierManager> findAllCarrierManagers() {
        log.info("FROM SERVISE: findAllCarrierManagers()");
        return managerRepository.findAll();
    }

    @Override
    @Transactional
    public CarrierManagerDto saveCarrierManager(CarrierManagerDto managerDto){
        CarrierManager manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);//entityMapper.managerDtoToEntity(managerDto);
        log.info("FROM SERVISE: saveCarrierManager() => manager: "+manager);
        managerRepository.save(manager);
        return managerDto;
    }

    @Override
    @Transactional
    public CarrierManagerDto deleteCarrierManagerById(Long id) {
        Optional<CarrierManager> manager = managerRepository.findById(id);
        managerRepository.deleteById(id);
        log.info("FROM SERVISE: deleteCarrierManagerById() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDTO(manager.get());
    }

    //Carrier
    @Override
    @Transactional(readOnly = true)
    public CarrierDto findCarrierById(Long id) {
        Optional<Carrier> carrier = carrierRepository.findById(id);
        log.info("FROM SERVISE: findCarrierById() => carrier: "+carrier);
        return CarrierMapper.INSTANSE.toDTO(carrier.get());
    }

    @Override
    @Transactional
    public CarrierDto saveCarrier(CarrierDto carrierDTO){
        Carrier carrier = CarrierMapper.INSTANSE.toEntity(carrierDTO);
        log.info("FROM SERVISE: saveCarrier() => carrier: "+carrier);
        carrierRepository.save(carrier);
        return carrierDTO;
    }

    @Override
    @Transactional
    public CarrierDto deleteCarrierById(Long id) {
        Optional<Carrier> carrier = carrierRepository.findById(id);
        log.info("FROM SERVISE: deleteCarrierById() => carrier: "+carrier);
        carrierRepository.deleteById(id);
        return CarrierMapper.INSTANSE.toDTO(carrier.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <Carrier> findAllCarriers() {
        log.info("FROM SERVISE: findAllCarriers()");
        return carrierRepository.findAll();
    }

    //FreightForwarder
    @Override
    @Transactional(readOnly = true)
    public FreightForwarderDto findFreightForwarderById(Long id) {
        Optional<FreightForwarder> forwarder = forwarderRepository.findById(id);
        log.info("FROM SERVISE: findFreightForwarderById() => forwarder: "+forwarder);
        return FreightForwarderMapper.INSTANSE.toDTO(forwarder.get());
    }

    @Override
    @Transactional
    public FreightForwarderDto saveFreightForwarder(FreightForwarderDto forwarderDto){
        FreightForwarder forwarder = FreightForwarderMapper.INSTANSE.toEntity(forwarderDto);
        log.info("FROM SERVISE: saveFreightForwarder() => forwarder: "+forwarder);
        forwarderRepository.save(forwarder);
        return forwarderDto;
    }

    @Override
    @Transactional
    public FreightForwarderDto deleteFreightForwarderById(Long id) {
        Optional<FreightForwarder> forwarder = forwarderRepository.findById(id);
        log.info("FROM SERVISE: deleteFreightForwarderById() => forwarder: "+forwarder);
        forwarderRepository.deleteById(id);
        return FreightForwarderMapper.INSTANSE.toDTO(forwarder.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <FreightForwarder> findAllForwarders() {
        log.info("FROM SERVISE: findAllForwarders()");
        return forwarderRepository.findAll();
    }

    //Order
    @Override
    @Transactional(readOnly = true)
    public CarriageRequestDto findOrderById(Long id) {
        Optional<CarriageRequest> order = orderRepository.findById(id);
        log.info("FROM SERVISE: findOrderById() => order: "+order);
        return CarriageRequestMapper.INSTANSE.toDTO(order.get());
    }

    @Override
    @Transactional
    public CarriageRequestDto saveOrder(CarriageRequestDto carriageRequestDTO){
        CarriageRequest order = CarriageRequestMapper.INSTANSE.toEntity(carriageRequestDTO);
        log.info("FROM SERVISE: saveOrder() => order: "+order);
        orderRepository.save(order);
        return carriageRequestDTO;
    }

    @Override
    @Transactional
    public CarriageRequestDto deleteOrderById(Long id) {
        Optional<CarriageRequest> order = orderRepository.findById(id);
        log.info("FROM SERVISE: deleteOrderById() => order: "+order);
        orderRepository.deleteById(id);
        return CarriageRequestMapper.INSTANSE.toDTO(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllOrders() {
        log.info("FROM SERVISE: findAllOrders()");
        return orderRepository.findAll();
    }

    //TruckPark
    @Override
    @Transactional(readOnly = true)
    public TruckParkDto findTruckParkById(Long id) {
        Optional<TruckPark> park = parkRepository.findById(id);
        log.info("FROM SERVISE: findTruckParkById() => park: "+park);
        return TruckParkMapper.INSTANSE.toDTO(park.get());
    }

    @Override
    @Transactional
    public TruckParkDto saveTruckPark(TruckParkDto parkDTO){
        TruckPark park = TruckParkMapper.INSTANSE.toEntity(parkDTO);
        log.info("FROM SERVISE: saveTruckPark() => park: "+park);
        parkRepository.save(park);
        return parkDTO;
    }

    @Override
    @Transactional
    public TruckParkDto deleteTruckParkById(Long id) {
        Optional<TruckPark> park = parkRepository.findById(id);
        log.info("FROM SERVISE: deleteTruckParkById() => park: "+park);
        parkRepository.deleteById(id);
        return TruckParkMapper.INSTANSE.toDTO(park.get());
    }

    @Transactional(readOnly = true)
    public List<TruckPark> findAllTruckParks(){
        log.info("FROM SERVISE: findAllTruckParks()");
        return parkRepository.findAll();
    }
}
