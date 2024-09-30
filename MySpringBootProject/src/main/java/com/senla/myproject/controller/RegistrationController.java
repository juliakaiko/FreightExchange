package com.senla.myproject.controller;

import com.senla.myproject.dto.*;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController/* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
@Tag(name="RegistrationController") // для swagger-а
public class RegistrationController {

    private final FreightExchangeService service;

    //@PostMapping("/managers")
    @RequestMapping(value="/managers", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity createCarrierManager (@RequestBody CarrierManagerDto managerDTO){
        log.info("FROM RegistrationController => Request to add new Carrier Manager: "+managerDTO);
        service.saveCarrierManager(managerDTO);
        return new ResponseEntity (managerDTO, HttpStatus.OK);
    }

    //@PostMapping("/orders")
    @RequestMapping(value="/orders", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity createCarriageRequest (@RequestBody CarriageRequestDto orderDTO){
        log.info("FROM RegistrationController => Request to add new Carriage Request: "+orderDTO);
        service.saveOrder(orderDTO);
        return new ResponseEntity (orderDTO, HttpStatus.OK);
    }

    //@PostMapping("/carriers")
    @RequestMapping(value="/carriers", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity createCarrier (@RequestBody CarrierDto carrierDTO){
        log.info("FROM RegistrationController => Request to add new Carrier: "+carrierDTO);
        service.saveCarrier(carrierDTO);
        return new ResponseEntity (carrierDTO, HttpStatus.OK);
    }

    //@PostMapping("/forwarders")
    @RequestMapping(value="/forwarders", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity createFreightForwarder (@RequestBody FreightForwarderDto forwarderDTO){
        log.info("FROM RegistrationController => Request to add new Freight Forwarder: "+forwarderDTO);
        service.saveFreightForwarder(forwarderDTO);
        return new ResponseEntity (forwarderDTO, HttpStatus.OK);
    }

    //@PostMapping("/truck_parks")
    @RequestMapping(value="/truck_parks", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity createTruckPark (@RequestBody TruckParkDto parkDTO){
        log.info("FROM RegistrationController => Request to add new Truck Park: "+parkDTO);
        service.saveTruckPark(parkDTO);
        return new ResponseEntity (parkDTO, HttpStatus.OK);
    }
}
