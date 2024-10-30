package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController/* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
//но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
@Tag(name="CarrierManager_UserController") // для swagger-а
@UserExceptionHandler
@PreAuthorize("hasAuthority('MANAGER')")
public class CarrierManagerUserController {

    private final FreightExchangeService service;

    public CarrierManager getAuthenticatedManager (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        CarrierManagerDto managerDto = service.findCarrierManagerByEmailIsLike(currentPrincipalName);
        CarrierManager manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);
        return manager;
    }

    @GetMapping("orders/take_order/{name}")
    //http://localhost:8080/orders/take_order/N-12345678
    //http://localhost:8080/orders/take_order/N-85632190
    //@PathVariable извлекает значения из пути URI
    public ResponseEntity  takeCarriageRequestByNameIsLike (@PathVariable("name") String orderName) {
        log.info("FROM CarrierManagerUserController => Request to take the Order with name: "+orderName);
        CarrierManager manager = getAuthenticatedManager();

        CarriageRequestDto orderDto = service.takeValidOrder(manager, orderName);

        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }

    @GetMapping("orders/cancel_order/{name}")
    //http://localhost:8080/orders/cancel_order/N-12345678
    public ResponseEntity  cancelCarriageRequestByNameIsLike (@PathVariable("name") String orderName) {
        log.info("FROM CarrierManagerUserController => Request to cancel the Order with name: "+orderName);
        CarrierManager manager = getAuthenticatedManager();
        CarriageRequestDto savedOrderDto = service.cancelOrder(manager,orderName);

        return ObjectUtils.isEmpty(savedOrderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedOrderDto);
    }

    @PutMapping("/managers")
    public ResponseEntity updateCarrierManager (@RequestBody CarrierManagerDto managerDto){
        log.info("FROM UserController => Request to update the CarrierManager: "+managerDto);
        CarrierManagerDto managerDto2 = service.findCarrierManagerById(managerDto.getId());
        if (managerDto2 != null) {
            managerDto.setCarriers(managerDto2.getCarriers());
            managerDto.setOrders(managerDto2.getOrders());
        }
        CarrierManagerDto savedManagerDto = service.saveCarrierManager(managerDto);
        return ObjectUtils.isEmpty(savedManagerDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedManagerDto);
    }

    @DeleteMapping("/managers/{id}")
    public ResponseEntity  deleteCarrierManager (@PathVariable("id") Long id){
        log.info("FROM UserController => Request to delete the CarrierManager by id: "+id);
        CarrierManagerDto managerDto = service.deleteCarrierManagerById(id);
        return ObjectUtils.isEmpty(managerDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(managerDto);
    }

    @PutMapping("/carriers")
    public ResponseEntity updateCarrier (@RequestBody CarrierDto carrierDto){
        log.info("FROM UserController => Request to update the Carrier: "+carrierDto);
        CarrierDto carrierDto2 = service.findCarrierById(carrierDto.getId());
        if (carrierDto2 != null)
            carrierDto.setPark(carrierDto2.getPark());
        CarrierDto savedCarrierDto = service.saveCarrier(carrierDto);
        return ObjectUtils.isEmpty(savedCarrierDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedCarrierDto);
    }

    @DeleteMapping("/carriers/{id}")
    public ResponseEntity  deleteCarrier (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the Carrier by id: "+id);
        CarrierDto carrierDto = service.deleteCarrierById(id);
        return ObjectUtils.isEmpty(carrierDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(carrierDto);
    }

    @PutMapping("/truck_parks")
    public ResponseEntity updateTruckPark (@RequestBody TruckParkDto parkDto){
        log.info("FROM UserController => Request to update the TruckPark: "+parkDto);
        TruckParkDto parkDto2 =  service.findTruckParkById(parkDto.getId());
        if (parkDto2 != null)
            parkDto.setCarrier(parkDto2.getCarrier());
        TruckParkDto savedParkDto = service.saveTruckPark(parkDto);
        return ObjectUtils.isEmpty(savedParkDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedParkDto);
    }

    @DeleteMapping("/truck_parks/{id}")
    public ResponseEntity  deleteTruckPark (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the TruckPark by id: "+id);
        TruckParkDto parkDto = service.deleteTruckParkById(id);
        return ObjectUtils.isEmpty(parkDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(parkDto);
    }
}
