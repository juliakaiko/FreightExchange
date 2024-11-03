package com.senla.myproject.service.impl;

import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.mapper.FreightForwarderMapper;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.model.Role;
import com.senla.myproject.model.User;
import com.senla.myproject.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final FreightExchangeServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtResponse registration(RegistrationRequest request) {
        User user = null;
        if (request.getRole().getAuthority().equals("MANAGER")){

            user = CarrierManager.builder()
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .firstName(request.getFirstName())
                            .surName(request.getSurName())
                            .role(Role.MANAGER)
                            .build();
            CarrierManager manager = (CarrierManager)user;
            CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
            userService.saveCarrierManager(managerDto);
        }

        if (request.getRole().getAuthority().equals("FORWARDER")){

            user = CarrierManager.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstName(request.getFirstName())
                    .surName(request.getSurName())
                    .role(Role.FORWARDER)
                    .build();
            FreightForwarder forwarder = (FreightForwarder)user;
            FreightForwarderDto forwarderDto =  FreightForwarderMapper.INSTANSE.toDto(forwarder);
            userService.saveFreightForwarder(forwarderDto);
        }

        var jwt = jwtTokenUtils.generateToken(user);
        return new JwtResponse(jwt);
    }

    public JwtResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getEmail());

        var jwt = jwtTokenUtils.generateToken(user);
        return new JwtResponse(jwt);
    }
}