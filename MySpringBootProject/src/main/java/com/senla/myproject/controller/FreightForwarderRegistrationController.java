package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController/* объединяет  @Controller и @ResponseBody => помечает класс как Spring MVC Controller,автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequiredArgsConstructor
@RequestMapping("/app")
@Tag(name="FreightForwarder_RegistrationController") // для swagger-а
@UserExceptionHandler
@PreAuthorize("hasAuthority('FORWARDER')")
@Slf4j
public class FreightForwarderRegistrationController {

    private final FreightExchangeService service;

    @PostMapping("/forwarders")
    public ResponseEntity <?> createFreightForwarder (@RequestBody @Valid FreightForwarderDto forwarderDTO){
        log.info("FROM RegistrationController => Request to add new Freight Forwarder: "+forwarderDTO);
        FreightForwarderDto savedForwarderDTO = service.saveFreightForwarder(forwarderDTO);
        return ResponseEntity.ok (savedForwarderDTO);
    }

}
