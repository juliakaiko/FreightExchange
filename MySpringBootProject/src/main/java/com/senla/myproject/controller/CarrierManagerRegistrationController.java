package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController/* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
//но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
@Tag(name="CarrierManager_RegistrationController") // для swagger-а
@UserExceptionHandler
public class CarrierManagerRegistrationController {

    private final FreightExchangeService service;

    @PostMapping("/managers")
    public ResponseEntity createCarrierManager (@RequestBody CarrierManagerDto managerDto){
        log.info("FROM RegistrationController => Request to add new Carrier Manager: "+managerDto);
        CarrierManagerDto savedManagerDto = service.saveCarrierManager(managerDto);
        return ResponseEntity.ok(savedManagerDto);
    }

    @PostMapping("/carriers")
    public ResponseEntity createCarrier (@RequestBody CarrierDto carrierDto){
        log.info("FROM RegistrationController => Request to add new Carrier: "+carrierDto);
        CarrierDto savedCarrierDto =  service.saveCarrier(carrierDto);
        return ResponseEntity.ok (savedCarrierDto);
    }

    @PostMapping("/truck_parks")
    public ResponseEntity createTruckPark (@RequestBody TruckParkDto parkDTO){
        log.info("FROM RegistrationController => Request to add new Truck Park: "+parkDTO);
        TruckParkDto savedParkDTO = service.saveTruckPark(parkDTO);
        return ResponseEntity.ok (savedParkDTO);
    }
}
