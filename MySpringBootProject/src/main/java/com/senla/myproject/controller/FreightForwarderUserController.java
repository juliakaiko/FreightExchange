package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController/* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
//но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
@Tag(name="Forwarder_UserController") // для swagger-а
@UserExceptionHandler
@PreAuthorize("hasAuthority('FORWARDER')")
public class FreightForwarderUserController {

    private final FreightExchangeService service;

    @PutMapping("/forwarders")
    public ResponseEntity updateFreightForwarder (@RequestBody FreightForwarderDto forwarderDto){
        log.info("FROM UserController => Request to update the FreightForwarder: "+forwarderDto);
        FreightForwarderDto forwarderDto2 = service.findFreightForwarderById(forwarderDto.getId());
        if (forwarderDto2 != null)
            forwarderDto.setOrders(forwarderDto2.getOrders());

        FreightForwarderDto savedForwarderDto =service.saveFreightForwarder(forwarderDto);

        return ObjectUtils.isEmpty(savedForwarderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedForwarderDto);
    }

    @DeleteMapping("/forwarders/{id}")
    public ResponseEntity  deleteFreightForwarder (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the FreightForwarder by id: "+id);
        FreightForwarderDto forwarderDto = service.deleteFreightForwarderById(id);
        return ObjectUtils.isEmpty(forwarderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(forwarderDto);
    }

    @PostMapping("/orders")
    public ResponseEntity createCarriageRequest (@RequestBody CarriageRequestDto orderDto){
        log.info("FROM RegistrationController => Request to add new Carriage Request: "+orderDto);
        CarriageRequestDto savedOrderDto = service.saveOrder(orderDto);
        return ResponseEntity.ok (savedOrderDto);
    }

    @PutMapping("/orders")
    public ResponseEntity updateCarriageRequest (@RequestBody CarriageRequestDto orderDto){
        log.info("FROM UserController => Request to update the CarriageRequest: "+orderDto);
        CarriageRequestDto orderDto2 = service.findOrderById(orderDto.getId());
        if (orderDto2 != null){
            orderDto.setForwarder(orderDto2.getForwarder());
            orderDto.setManager(orderDto2.getManager());
        }
        CarriageRequestDto savedOrderDto = service.saveOrder(orderDto);

        return ObjectUtils.isEmpty(savedOrderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedOrderDto);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity  deleteCarriageRequest (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to delete the CarriageRequest by id: "+id);
        CarriageRequestDto orderDto = service.deleteOrderById(id);
        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }
}
