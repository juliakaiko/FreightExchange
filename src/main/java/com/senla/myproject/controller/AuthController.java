package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.AuthenticationRequest;
import com.senla.myproject.dto.JwtResponse;
import com.senla.myproject.dto.RegistrationRequest;
import com.senla.myproject.service.impl.AuthenticationServiceImpl;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@UserExceptionHandler
@Tag(name="AuthController")
@Slf4j
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/registration")
    public JwtResponse registration(@RequestBody @Valid RegistrationRequest request) {
        log.info("User registration request: {}",request.getEmail());
        return authenticationService.registrate(request);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody @Valid AuthenticationRequest request) {
        log.info("User authentication request: {}",request.getEmail());
        JwtResponse response;
        try {
            response = authenticationService.authenticate(request);
        }catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ObjectUtils.isEmpty(response)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }
}
