package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.*;
import com.senla.myproject.model.*;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j // для логирования
@Tag(name="UserController") // для swagger-а
@UserExceptionHandler
public class UserController {

    private final FreightExchangeService service;

    //ендпоинт
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello, World!";
    }

    //CarrierManager
    @GetMapping("/managers/{id}")
    public ResponseEntity  getCarrierManager (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the CarrierManager by id: "+id);
        CarrierManagerDto managerDTO = service.findCarrierManagerById(id);
        if (managerDTO == null)
            return new ResponseEntity("CarrierManager with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(managerDTO, HttpStatus.OK);
    }

    @GetMapping("/managers")
    public List<CarrierManager> getAllCarrierManagers(){
        log.info("FROM UserController => Request to find all CarrierManagers");
        return service.findAllCarrierManagers();
    }

    @DeleteMapping("/managers/{id}")
    public ResponseEntity  deleteCarrierManager (@PathVariable("id") Long id){
        log.info("FROM UserController => Request to delete the CarrierManager by id: "+id);
        CarrierManagerDto managerDTO = service.findCarrierManagerById(id);
        service.deleteCarrierManagerById(id);
        if (managerDTO == null)
            return new ResponseEntity ("CarrierManager with id: " +id+ " not found",HttpStatus.NOT_FOUND);
        return new ResponseEntity (managerDTO,HttpStatus.OK);
    }

    //Order
    @GetMapping("/orders/{id}")
    public ResponseEntity  getCarriageRequest (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the CarriageRequest by id: "+id);
        CarriageRequestDto orderDTO = service.findOrderById(id);
        if (orderDTO == null)
            return new ResponseEntity("Order with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<CarriageRequest> getAllCarriageRequests(){
        log.info("FROM UserController => Request to find all CarriageRequests");
        return service.findAllOrders();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity  deleteCarriageRequest (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the CarriageRequest by id: "+id);
        CarriageRequestDto orderDTO = service.findOrderById(id);
        service.deleteOrderById(id);
        if (orderDTO == null)
            return new ResponseEntity("Order with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }

    //Carrier
    @GetMapping("/carriers/{id}")
    public ResponseEntity  getCarriers (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the Carrier by id: "+id);
        CarrierDto carrierDto = service.findCarrierById(id);
        if (carrierDto == null)
            return new ResponseEntity("Carrier with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(carrierDto, HttpStatus.OK);
    }

    @GetMapping("/carriers")
    public List<Carrier> getAllCarriers(){
        log.info("FROM UserController => Request to find all Carriers");
        return service.findAllCarriers();
    }

    @DeleteMapping("/carriers/{id}")
    public ResponseEntity  deleteCarrier (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the Carrier by id: "+id);
        CarrierDto carrierDto = service.findCarrierById(id);
        service.deleteCarrierById(id);
        if (carrierDto == null)
            return new ResponseEntity("Carrier with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(carrierDto, HttpStatus.OK);
    }

    // FreightForwarder
    @GetMapping("/forwarders/{id}")
    public ResponseEntity  getFreightForwarder (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the FreightForwarder by id: "+id);
        FreightForwarderDto forwarderDto = service.findFreightForwarderById(id);
        if (forwarderDto == null)
            return new ResponseEntity("FreightForwarder with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(forwarderDto, HttpStatus.OK);
    }

    @GetMapping("/forwarders")
    public List<FreightForwarder> getAlFreightForwarders(){
        log.info("FROM UserController => Request to find all FreightForwarders");
        return service.findAllForwarders();
    }

    @DeleteMapping("/forwarders/{id}")
    public ResponseEntity  deleteFreightForwarder (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the FreightForwarder by id: "+id);
        FreightForwarderDto forwarderDto = service.findFreightForwarderById(id);
        service.deleteFreightForwarderById(id);
        if (forwarderDto == null)
            return new ResponseEntity("FreightForwarder with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(forwarderDto, HttpStatus.OK);
    }

    // TruckPark
    @GetMapping("/truck_parks/{id}")
    public ResponseEntity  getTruckPark (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the TruckPark by id: "+id);
        TruckParkDto parkDto = service.findTruckParkById(id);
        if (parkDto == null)
            return new ResponseEntity("TruckPark with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(parkDto, HttpStatus.OK);
    }

    @GetMapping("/truck_parks")
    public List<TruckPark> getAllTruckParks(){
        log.info("FROM UserController => Request to find all TruckParks");
        return service.findAllTruckParks();
    }

    @DeleteMapping("/truck_parks/{id}")
    public ResponseEntity  deleteTruckPark (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the TruckPark by id: "+id);
        TruckParkDto parkDto = service.findTruckParkById(id);
        service.deleteTruckParkById(id);
        if (parkDto == null)
            return new ResponseEntity("TruckPark with id: " + id + " not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(parkDto, HttpStatus.OK);
    }


}
