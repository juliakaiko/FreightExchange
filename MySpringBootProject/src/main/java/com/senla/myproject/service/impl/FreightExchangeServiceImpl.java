package com.senla.myproject.service.impl;

import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.exceptions.NotFoundException;
import com.senla.myproject.exceptions.OrderNotValidException;
import com.senla.myproject.mapper.*;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.repository.*;
import com.senla.myproject.model.*;
import com.senla.myproject.dto.*;
import com.senla.myproject.service.FreightExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreightExchangeServiceImpl implements FreightExchangeService {
    private final CarrierRepository carrierRepository;
    private final CarrierManagerRepository managerRepository;
    private final FreightForwarderRepository forwarderRepository;
    private final CarriageRequestRepository orderRepository;
    private final TruckParkRepository parkRepository;

    //CarrierManager
    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerById(Long id)  {
        /*  Optional.of  бросит исключение NullPointerException, если ему передать значение null в качестве
        параметра. Optional.ofNullable вернёт Optional, не содержащий значение, если ему передать null.*/
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found with id " + id)));
        log.info("FROM SERVISE: findCarrierManagerById() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email){
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findCarrierManagerWithEntityGraphByEmail(email)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by email " + email)));
        log.info("FROM SERVISE: findCarrierManagerWithEntityGraphByEmail() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerByEmailIsLike(String email){
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findCarrierManagerByEmailIsLike(email)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by email " + email)));
        log.info("FROM SERVISE: findCarrierManagerByEmailIsLike() => manager: "+manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierManager> findAllCarrierManagers() {
        log.info("FROM SERVISE: findAllCarrierManagers()");
        return managerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarrierManagerDto> findAllManagersNativeWithPagination(int page, int size) {
        log.info("FROM SERVISE: findAllManagersNativeWithPaginatio()");
        var pageable  = PageRequest.of(page,size, Sort.by("id"));
        Page<CarrierManager> managerList = managerRepository.findAllManagersNative(pageable);
        Page<CarrierManagerDto> managerDtos = managerList.map(CarrierManagerMapper.INSTANSE::toDto);
        return managerDtos;
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
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    //Carrier
    @Override
    @Transactional(readOnly = true)
    public CarrierDto findCarrierById(Long id) {
        Optional<Carrier> carrier = carrierRepository.findById(id);
        log.info("FROM SERVISE: findCarrierById() => carrier: "+carrier);
        return CarrierMapper.INSTANSE.toDto(carrier.get());
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
        Optional<Carrier> carrier = Optional.ofNullable(carrierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by id " + id)));
        log.info("FROM SERVISE: deleteCarrierById() => carrier: "+carrier);
        carrierRepository.deleteById(id);
        return CarrierMapper.INSTANSE.toDto(carrier.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <Carrier> findAllCarriers() {
        log.info("FROM SERVISE: findAllCarriers()");
        return carrierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarrierDto> findAllCarriersNativeWithPagination(int page, int size) {
        log.info("FROM SERVISE: findAllCarriersNativeWithPagination()");
        var pageable  = PageRequest.of(page,size, Sort.by("id"));
        Page<Carrier> carrierList = carrierRepository.findAllCarriersNative(pageable);
        Page<CarrierDto> carrierDtos = carrierList.map(CarrierMapper.INSTANSE::toDto);
        return carrierDtos;
    }

    //FreightForwarder
    @Override
    @Transactional(readOnly = true)
    public FreightForwarderDto findFreightForwarderById(Long id) {
        Optional<FreightForwarder> forwarder = Optional.ofNullable(forwarderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FreightForwarder wasn't found by id " + id)));
        log.info("FROM SERVISE: findFreightForwarderById() => forwarder: "+forwarder);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
    }

    @Override
    @Transactional(readOnly = true)
    public FreightForwarderDto findFreightForwarderByEmailIsLike (String email){
        Optional<FreightForwarder> forwarder = Optional.ofNullable(forwarderRepository.findFreightForwarderByEmailIsLike(email)
                .orElseThrow(() -> new NotFoundException("FreightForwarder wasn't found with email " + email)));
        log.info("FROM SERVISE: findFreightForwarderByEmailIsLike() => forwarder: "+forwarder);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
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
        Optional<FreightForwarder> forwarder = Optional.ofNullable(forwarderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FreightForwarder wasn't found by id " + id)));
        log.info("FROM SERVISE: deleteFreightForwarderById() => forwarder: "+forwarder);
        forwarderRepository.deleteById(id);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <FreightForwarder> findAllForwarders() {
        log.info("FROM SERVISE: findAllForwarders()");
        return forwarderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FreightForwarderDto> findAllForwardersNativeWithPagination(int page, int size) {
        log.info("FROM SERVISE: findAllForwardersNativeWithPagination()");
        var pageable  = PageRequest.of(page,size, Sort.by("id"));
        Page<FreightForwarder> forwarderList = forwarderRepository.findAllFreightForwardersNative(pageable);
        Page<FreightForwarderDto> forwarderDtos = forwarderList.map(FreightForwarderMapper.INSTANSE::toDto);
        return forwarderDtos;
    }

    //Order
    @Override
    @Transactional(readOnly = true)
    public CarriageRequestDto findOrderById(Long id) {
        Optional<CarriageRequest> order = Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order wasn't found by id " + id)));
        log.info("FROM SERVISE: findOrderById() => order: "+order);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarriageRequestDto findOrderByName(String orderName) {
        Optional<CarriageRequest> order = Optional.ofNullable(orderRepository.findByOrderNameIsLike(orderName).
                orElseThrow(() -> new NotFoundException("Order wasn't found by name " + orderName)));;
        log.info("FROM SERVISE: findOrderByName() => order: "+order);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional
    public CarriageRequestDto takeValidOrder(CarrierManager manager, String orderName) {
        Optional<CarriageRequest> order = orderRepository.findByOrderNameIsLike(orderName);
        log.info("FROM SERVISE: takeValidOrder() => order: "+order);
        if (order.get().getValid() == true) {
            order.get().setValid(false);
            order.get().setManager(manager);
            manager.getOrders().add(order.get());
            //Почему сохраняет изменения?
            //managerRepository.save(manager);
            //orderRepository.save(order.get());
        }else {
            throw new OrderNotValidException("This order is not valid");
        }
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional
    public CarriageRequestDto cancelOrder(CarrierManager manager, String orderName){
        Optional<CarriageRequest> orderFromDb = orderRepository.findByOrderNameIsLike(orderName);
        log.info("FROM SERVISE: cancelOrder() => order: "+orderFromDb);
        Set<CarriageRequest> managerOrders = manager.getOrders();
        boolean found = false;
        for (CarriageRequest order: managerOrders){
            if(order.equals(orderFromDb.get())){
                orderFromDb.get().setValid(true);
                orderFromDb.get().setManager(null);
                manager.getOrders().remove(orderFromDb);
                //orderRepository.save(orderFromDb.get());
                //managerRepository.save(manager);
                found = true;
            }
        }

        if (found==false){
            throw new NotFoundException("This manager doesn't have such order");
        }
        return CarriageRequestMapper.INSTANSE.toDto(orderFromDb.get());
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
        Optional<CarriageRequest> order = Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order wasn't found by id " + id)));
        log.info("FROM SERVISE: deleteOrderById() => order: "+order);
        orderRepository.deleteById(id);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllOrders() {
        log.info("FROM SERVISE: findAllOrders()");
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllFreeOrders() {
        log.info("FROM SERVISE: findAllFreeOrders()");
        List <CarriageRequest> orderList = orderRepository.findAll();
        List <CarriageRequest> freeOrderList = new ArrayList<>();
        for (CarriageRequest orderFromDb: orderList){
            if (orderFromDb.getValid()==true){
                freeOrderList.add(orderFromDb);
            }
        }
        return freeOrderList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarriageRequestDto> findAllOrdersNativeWithPagination(int page, int size) {
        log.info("FROM SERVISE: findAllOrdersNativeWithPagination()");
        var pageable  = PageRequest.of(page,size, Sort.by("id"));
        Page<CarriageRequest> orderList = orderRepository.findAllOrdersNative(pageable);
        Page<CarriageRequestDto> orderDtos = orderList.map(CarriageRequestMapper.INSTANSE::toDto);
        return orderDtos;
    }

    //TruckPark
    @Override
    @Transactional(readOnly = true)
    public TruckParkDto findTruckParkById(Long id) {
        Optional<TruckPark> park = Optional.ofNullable(parkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TruckPark wasn't found by id " + id)));
        log.info("FROM SERVISE: findTruckParkById() => park: "+park);
        return TruckParkMapper.INSTANSE.toDto(park.get());
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
        Optional<TruckPark> park = Optional.ofNullable(parkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TruckPark wasn't found by id " + id)));
        log.info("FROM SERVISE: deleteTruckParkById() => park: "+park);
        parkRepository.deleteById(id);
        return TruckParkMapper.INSTANSE.toDto(park.get());
    }

    @Transactional(readOnly = true)
    public List<TruckPark> findAllTruckParks(){
        log.info("FROM SERVISE: findAllTruckParks()");
        return parkRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TruckParkDto> findAllTruckParksNativeWithPagination(int page, int size) {
        log.info("FROM SERVISE: findAllTruckParksNativeWithPagination()");
        var pageable  = PageRequest.of(page,size);
        Page<TruckPark> parkList = parkRepository.findAllTruckParksNative(pageable);
        Page<TruckParkDto> parkDtos = parkList.map(TruckParkMapper.INSTANSE::toDto);
        return parkDtos;
    }
}
