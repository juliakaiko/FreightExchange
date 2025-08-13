package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@Tag(name="CarrierManager_UserController")
@UserExceptionHandler
@PreAuthorize("hasAuthority('MANAGER')")
@Slf4j
public class CarrierManagerController {

    private final FreightExchangeService service;

    public CarrierManager getAuthenticatedManager (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        CarrierManagerDto managerDto = service.findCarrierManagerByEmailIsLike(currentPrincipalName);
        CarrierManager manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);
        return manager;
    }

    @PutMapping("orders/take_order/{name}")
    public ResponseEntity <?> takeCarriageRequestByNameIsLike (@PathVariable("name") String orderName) {
        log.info("Request to take the Order with name: {}", orderName);
        CarrierManager manager = getAuthenticatedManager();

        CarriageRequestDto orderDto = service.takeValidOrder(manager, orderName);

        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }

    @PutMapping("orders/cancel_order/{name}")
    public ResponseEntity <?> cancelCarriageRequestByNameIsLike (@PathVariable("name") String orderName) {
        log.info("Request to cancel the Order with name: {}", orderName);
        CarrierManager manager = getAuthenticatedManager();
        CarriageRequestDto savedOrderDto = service.cancelOrder(manager,orderName);

        return ObjectUtils.isEmpty(savedOrderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedOrderDto);
    }

    @GetMapping("manager/orders")
    public ResponseEntity <?> showManagerOrders () {
        CarrierManager manager = getAuthenticatedManager();
        log.info("Request to show all orders of the manager: {}", manager);

        Set<CarriageRequest> managerOrderList = manager.getOrders();

        return ObjectUtils.isEmpty(managerOrderList)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(managerOrderList);
    }

    @PutMapping("/managers")
    public ResponseEntity <?> updateCarrierManager (@RequestBody @Valid CarrierManagerDto managerDto){
        log.info("Request to update the CarrierManager: {}",managerDto);
        CarrierManagerDto managerDto2 = service.findCarrierManagerById(managerDto.getId());
        CarrierManager authenticatedManager = getAuthenticatedManager();

        CarrierManagerDto savedManagerDto;
        if (managerDto2 != null & managerDto2.getId().equals(authenticatedManager.getId())) {
            managerDto.setCarriers(managerDto2.getCarriers());
            managerDto.setOrders(managerDto2.getOrders());
            savedManagerDto = service.saveCarrierManager(managerDto);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(savedManagerDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedManagerDto);
    }

    @DeleteMapping("/managers/{id}")
    public ResponseEntity <?> deleteCarrierManager (@PathVariable("id") Long id){
        log.info("Request to delete the CarrierManager by id: {}",id);
        CarrierManagerDto managerDto = service.findCarrierManagerById(id);
        CarrierManager authenticatedManager = getAuthenticatedManager();

        CarrierManagerDto deletedManagerDto;
        if (managerDto.getId().equals(authenticatedManager.getId())) {
            deletedManagerDto = service.deleteCarrierManagerById(id);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(deletedManagerDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(deletedManagerDto);
    }

    @PostMapping("/carriers")
    public ResponseEntity <?> createCarrier (@RequestBody @Valid CarrierDto carrierDto){
        log.info("Request to add new Carrier: {}",carrierDto);
        CarrierDto savedCarrierDto =  service.saveCarrier(carrierDto);
        return ResponseEntity.ok (savedCarrierDto);
    }

    @PutMapping("/carriers")
    public ResponseEntity <?> updateCarrier (@RequestBody @Valid CarrierDto carrierDto){
        log.info("Request to update the Carrier: {}", carrierDto);
        CarrierDto carrierDto2 = service.findCarrierById(carrierDto.getId());
        CarrierDto savedCarrierDto = null;
        if (carrierDto2 != null) {
            savedCarrierDto = service.saveCarrier(carrierDto);
        }
        return ObjectUtils.isEmpty(savedCarrierDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedCarrierDto);
    }

    @DeleteMapping("/carriers/{id}")
    public ResponseEntity <?> deleteCarrier (@PathVariable("id") Long id) {
        log.info("Request to delete the Carrier by id: {}",id);
        CarrierDto carrierDto = service.deleteCarrierById(id);
        return ObjectUtils.isEmpty(carrierDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(carrierDto);
    }

    @DeleteMapping("/truck_parks/{id}")
    public ResponseEntity <?> deleteTruckPark (@PathVariable("id") Long id) {
        log.info("Request to delete the TruckPark by id: {}",id);
        TruckParkDto parkDto = service.deleteTruckParkById(id);
        return ObjectUtils.isEmpty(parkDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(parkDto);
    }

    @PostMapping("/truck_parks")
    public ResponseEntity <?> createTruckPark (@RequestBody @Valid TruckParkDto parkDTO){
        log.info("Request to add new Truck Park: {}",parkDTO);
        TruckParkDto savedParkDTO = service.saveTruckPark(parkDTO);
        return ResponseEntity.ok (savedParkDTO);
    }

    @PutMapping("/truck_parks")
    public ResponseEntity <?> updateTruckPark (@RequestBody @Valid TruckParkDto parkDto){
        log.info("Request to update the TruckPark: {}", parkDto);
        TruckParkDto parkDto2 =  service.findTruckParkById(parkDto.getId());
        if (parkDto2 != null)
            parkDto.setCarrier(parkDto2.getCarrier());
        TruckParkDto savedParkDto = service.saveTruckPark(parkDto);
        return ObjectUtils.isEmpty(savedParkDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedParkDto);
    }
}
