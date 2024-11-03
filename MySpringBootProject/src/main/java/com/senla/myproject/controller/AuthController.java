package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.AuthenticationRequest;
import com.senla.myproject.dto.JwtResponse;
import com.senla.myproject.dto.RegistrationRequest;
import com.senla.myproject.service.impl.AuthenticationServiceImpl;
import com.senla.myproject.service.impl.FreightExchangeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController /* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
//но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
@RequestMapping("/auth")
@RequiredArgsConstructor
@UserExceptionHandler
@Tag(name="AuthController") // для swagger-а
@Slf4j // для логирования
public class AuthController {

    private final FreightExchangeServiceImpl service;

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/registration")
    public JwtResponse registration(@RequestBody @Valid RegistrationRequest request) {
        log.info("FROM AuthController => User registration request : "+request.getEmail());
        return authenticationService.registration(request);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody @Valid AuthenticationRequest request) {
        log.info("FROM AuthController => User authentication request : "+request.getEmail());
        JwtResponse response = null;
        try {
            response = authenticationService.authentication(request);
        }catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ObjectUtils.isEmpty(response)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }
}
