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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreightExchangeServiceImpl implements FreightExchangeService, UserDetailsService {
    private final CarrierRepository carrierRepository;
    private final CarrierManagerRepository managerRepository;
    private final FreightForwarderRepository forwarderRepository;
    private final CarriageRequestRepository orderRepository;
    private final TruckParkRepository parkRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = null;
        Optional<CarrierManager> manager = managerRepository.findCarrierManagerByEmailIsLike(username);
        Optional<FreightForwarder> forwarder = forwarderRepository.findFreightForwarderByEmailIsLike(username);
        if (manager.isPresent()){
            userFromDb = manager.get();
        }
        if (forwarder.isPresent()){
            userFromDb = forwarder.get();
        }
        Optional <User> optionalUser = Optional.ofNullable(userFromDb);
        return optionalUser.map(user -> new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),
                Collections.singleton(optionalUser.get().getRole())
        )).orElseThrow(() -> new UsernameNotFoundException(username+ " not found"));
    }

    //CarrierManager
    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerById(Long id)  {
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found with id " + id)));
        log.info("findCarrierManagerById(): {}",manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerWithEntityGraphByEmail(String email){
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findCarrierManagerWithEntityGraphByEmail(email)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by email " + email)));
        log.info("findCarrierManagerWithEntityGraphByEmail(): {}",manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarrierManagerDto findCarrierManagerByEmailIsLike(String email){
        Optional<CarrierManager> manager = Optional.ofNullable(managerRepository.findCarrierManagerByEmailIsLike(email)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by email " + email)));
        log.info("findCarrierManagerByEmailIsLike(): {}",manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierManager> findAllCarrierManagers() {
        return managerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarrierManagerDto> findAllManagersNativeWithPagination(Integer page, Integer size) {
        var pageable  = PageRequest.of(page,size, Sort.by("id"));
        Page<CarrierManager> managerList = managerRepository.findAllManagersNative(pageable);
        Page<CarrierManagerDto> managerDtos = managerList.map(CarrierManagerMapper.INSTANSE::toDto);
        return managerDtos;
    }

    @Override
    @Transactional
    public CarrierManagerDto saveCarrierManager(CarrierManagerDto managerDto){
        CarrierManager manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);//entityMapper.managerDtoToEntity(managerDto);
        log.info("saveCarrierManager(): {}",manager);
        managerRepository.save(manager);
        return managerDto;
    }

    @Override
    @Transactional
    public CarrierManagerDto deleteCarrierManagerById(Long id) {
        Optional<CarrierManager> manager = managerRepository.findById(id);
        managerRepository.deleteById(id);
        log.info("deleteCarrierManagerById(): {}",manager);
        return CarrierManagerMapper.INSTANSE.toDto(manager.get());
    }

    //Carrier
    @Override
    @Transactional(readOnly = true)
    public CarrierDto findCarrierById(Long id) {
        Optional<Carrier> carrier = carrierRepository.findById(id);
        log.info("findCarrierById(): {}",carrier);
        return CarrierMapper.INSTANSE.toDto(carrier.get());
    }

    @Override
    @Transactional
    public CarrierDto saveCarrier(CarrierDto carrierDTO){
        Carrier carrier = CarrierMapper.INSTANSE.toEntity(carrierDTO);
        log.info("saveCarrier(): {}",carrier);
        carrierRepository.save(carrier);
        return carrierDTO;
    }

    @Override
    @Transactional
    public CarrierDto deleteCarrierById(Long id) {
        Optional<Carrier> carrier = Optional.ofNullable(carrierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CarrierManager wasn't found by id " + id)));
        log.info("deleteCarrierById(): {}",carrier);
        carrierRepository.deleteById(id);
        return CarrierMapper.INSTANSE.toDto(carrier.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <Carrier> findAllCarriers() {
        return carrierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarrierDto> findAllCarriersNativeWithPagination(Integer page, Integer size) {
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
        log.info("findFreightForwarderById(): {}",forwarder);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
    }

    @Override
    @Transactional(readOnly = true)
    public FreightForwarderDto findFreightForwarderByEmailIsLike (String email){
        Optional<FreightForwarder> forwarder = Optional.ofNullable(forwarderRepository.findFreightForwarderByEmailIsLike(email)
                .orElseThrow(() -> new NotFoundException("FreightForwarder wasn't found with email " + email)));
        log.info("findFreightForwarderByEmailIsLike(): {}",forwarder);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
    }

    @Override
    @Transactional
    public FreightForwarderDto saveFreightForwarder(FreightForwarderDto forwarderDto){
        FreightForwarder forwarder = FreightForwarderMapper.INSTANSE.toEntity(forwarderDto);
        log.info("saveFreightForwarder(): {}",forwarder);
        forwarderRepository.save(forwarder);
        return forwarderDto;
    }

    @Override
    @Transactional
    public FreightForwarderDto deleteFreightForwarderById(Long id) {
        Optional<FreightForwarder> forwarder = Optional.ofNullable(forwarderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FreightForwarder wasn't found by id " + id)));
        log.info("deleteFreightForwarderById(): {}",forwarder);
        forwarderRepository.deleteById(id);
        return FreightForwarderMapper.INSTANSE.toDto(forwarder.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List <FreightForwarder> findAllForwarders() {
        return forwarderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FreightForwarderDto> findAllForwardersNativeWithPagination(Integer page, Integer size) {
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
        log.info("findOrderById(): {}",order);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CarriageRequestDto findOrderByName(String orderName) {
        Optional<CarriageRequest> order = Optional.ofNullable(orderRepository.findByOrderNameIsLike(orderName).
                orElseThrow(() -> new NotFoundException("Order wasn't found by name " + orderName)));;
        log.info("findOrderByName(): {}",order);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional
    public CarriageRequestDto takeValidOrder(CarrierManager manager, String orderName) {
        Optional<CarriageRequest> order = orderRepository.findByOrderNameIsLike(orderName);
        log.info("takeValidOrder(): {}",order);
        if (order.get().getValid() == true) {
            order.get().setValid(false);
            order.get().setManager(manager);
            manager.getOrders().add(order.get());
        }else {
            throw new OrderNotValidException("This order is not valid");
        }
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional
    public CarriageRequestDto cancelOrder(CarrierManager manager, String orderName){
        Optional<CarriageRequest> orderFromDb = orderRepository.findByOrderNameIsLike(orderName);
        log.info("cancelOrder(): {}",orderFromDb);
        Set<CarriageRequest> managerOrders = manager.getOrders();
        boolean found = false;
        for (CarriageRequest order: managerOrders){
            if(order.equals(orderFromDb.get())){
                orderFromDb.get().setValid(true);
                orderFromDb.get().setManager(null);
                manager.getOrders().remove(orderFromDb);
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
        log.info("saveOrder(): {}",order);
        orderRepository.save(order);
        return carriageRequestDTO;
    }

    @Override
    @Transactional
    public CarriageRequestDto deleteOrderById(Long id) {
        Optional<CarriageRequest> order = Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order wasn't found by id " + id)));
        log.info("deleteOrderById(): {}",order);
        orderRepository.deleteById(id);
        return CarriageRequestMapper.INSTANSE.toDto(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageRequest> findAllFreeOrders() {
        log.info("findAllFreeOrders()");
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
    public Page<CarriageRequestDto> findAllOrdersNativeWithPagination(Integer page, Integer size) {
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
        log.info("findTruckParkById(): {}",park);
        return TruckParkMapper.INSTANSE.toDto(park.get());
    }

    @Override
    @Transactional
    public TruckParkDto saveTruckPark(TruckParkDto parkDTO){
        TruckPark park = TruckParkMapper.INSTANSE.toEntity(parkDTO);
        log.info("saveTruckPark(): {}",park);
        parkRepository.save(park);
        return parkDTO;
    }

    @Override
    @Transactional
    public TruckParkDto deleteTruckParkById(Long id) {
        Optional<TruckPark> park = Optional.ofNullable(parkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TruckPark wasn't found by id " + id)));
        log.info("deleteTruckParkById(): {}",park);
        parkRepository.deleteById(id);
        return TruckParkMapper.INSTANSE.toDto(park.get());
    }

    @Transactional(readOnly = true)
    public List<TruckPark> findAllTruckParks(){
        return parkRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TruckParkDto> findAllTruckParksNativeWithPagination(Integer page, Integer size) {
        var pageable  = PageRequest.of(page,size);
        Page<TruckPark> parkList = parkRepository.findAllTruckParksNative(pageable);
        Page<TruckParkDto> parkDtos = parkList.map(TruckParkMapper.INSTANSE::toDto);
        return parkDtos;
    }
}
