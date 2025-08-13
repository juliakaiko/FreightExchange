package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.mapper.FreightForwarderMapper;
import com.senla.myproject.model.FreightForwarder;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@Tag(name="Forwarder_UserController")
@UserExceptionHandler
@PreAuthorize("hasAuthority('FORWARDER')")
@Slf4j
public class FreightForwarderController {

    private final FreightExchangeService service;

    public FreightForwarder getAuthenticatedForwarder (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        FreightForwarderDto forwarderDto = service.findFreightForwarderByEmailIsLike(currentPrincipalName);
        FreightForwarder forwarder =  FreightForwarderMapper.INSTANSE.toEntity(forwarderDto);
        return forwarder;
    }

    @PutMapping("/forwarders")
    public ResponseEntity <?> updateFreightForwarder (@RequestBody @Valid FreightForwarderDto forwarderDto){
        log.info("Request to update the FreightForwarder: {}",forwarderDto);
        FreightForwarderDto forwarderDto2 = service.findFreightForwarderById(forwarderDto.getId());
        if (forwarderDto2 != null)
            forwarderDto.setOrders(forwarderDto2.getOrders());

        FreightForwarder forwarder = getAuthenticatedForwarder ();
        FreightForwarderDto savedForwarderDto;

        if (forwarderDto2.getId().equals(forwarder.getId())){
            savedForwarderDto = service.saveFreightForwarder(forwarderDto);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(savedForwarderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedForwarderDto);
    }

    @DeleteMapping("/forwarders/{id}")
    public ResponseEntity <?> deleteFreightForwarder (@PathVariable("id") Long id) {
        log.info("Request to delete the FreightForwarder by id: {}", id);
        FreightForwarder forwarder = getAuthenticatedForwarder ();
        FreightForwarderDto forwarderDto;

        if (id.equals(forwarder.getId())){
            forwarderDto = service.deleteFreightForwarderById(id);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(forwarderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(forwarderDto);
    }

    @PostMapping("/orders")
    public ResponseEntity <?> createCarriageRequest (@RequestBody @Valid CarriageRequestDto orderDto){
        log.info("Request to add new Carriage Request: {}",orderDto);
        FreightForwarder forwarder = getAuthenticatedForwarder ();
        orderDto.setForwarder(forwarder);
        CarriageRequestDto savedOrderDto = service.saveOrder(orderDto);
        CarriageRequestDto fromDbOrderDto = service.findOrderByName(savedOrderDto.getOrderName());
        return ResponseEntity.ok (fromDbOrderDto);
    }

    @PutMapping("/orders")
    public ResponseEntity <?> updateCarriageRequest (@RequestBody @Valid CarriageRequestDto orderDto){
        log.info("Request to update the CarriageRequest: {}",orderDto);
        CarriageRequestDto orderDto2 = service.findOrderByName(orderDto.getOrderName());
        if (orderDto2 != null){
            orderDto.setForwarder(orderDto2.getForwarder());
            orderDto.setManager(orderDto2.getManager());
        }
        FreightForwarder forwarder = getAuthenticatedForwarder ();
        CarriageRequestDto savedOrderDto;
        //Only the FreightForwarder who created the order can update it.
        if (service.findOrderById(orderDto2.getId()).getForwarder().getId().equals(forwarder.getId())){
            orderDto.setId(orderDto2.getId());
            savedOrderDto  = service.saveOrder(orderDto);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(savedOrderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(savedOrderDto);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity <?> deleteCarriageRequest (@PathVariable("id") Long id) {
        log.info("Request to delete the CarriageRequest by id: {}",id);
        FreightForwarder forwarder = getAuthenticatedForwarder ();
        CarriageRequestDto orderDto;
        //Only the FreightForwarder who created the order can delete it.
        if (service.findOrderById(id).getForwarder().getId().equals(forwarder.getId())){
            orderDto = service.deleteOrderById(id);
        }else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }
}
