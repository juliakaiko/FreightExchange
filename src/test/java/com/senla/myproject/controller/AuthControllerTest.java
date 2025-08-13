package com.senla.myproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.configuration.JwtRequestFilter;
import com.senla.myproject.configuration.SpringSecurityConfiguration;
import com.senla.myproject.dto.AuthenticationRequest;
import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.dto.JwtResponse;
import com.senla.myproject.dto.RegistrationRequest;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.User;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.service.impl.AuthenticationServiceImpl;
import com.senla.myproject.service.impl.FreightExchangeServiceImpl;
import com.senla.myproject.util.CarrierManagerGenerator;
import com.senla.myproject.util.JwtTokenUtils;
import com.senla.myproject.util.UserGenerator;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAKey;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.context.support.TestExecutionEvent.TEST_METHOD;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest//(controllers = AuthController.class)
@Slf4j // для логирования
//@WithMockUser // тестрирование с аутентифицированным пользователем
//@Import(SpringSecurityConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
//@ContextConfiguration
//@WithUserDetails //(setupBefore=TEST_METHOD, userDetailsServiceBeanName="inMemoryUserDetailsManager",value="user@test.by")
class AuthControllerTest {

    @MockBean // объект добавляет в Spring ApplicationContext в отличие от @Mock => заменяет бин на мок в контексте
    private FreightExchangeServiceImpl service;

    @MockBean
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenUtils jwtTokenUtils;

    private String token="secrettoken";

    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_MANAGER");
        userDetails =
                new org.springframework.security.core.userdetails.User ("user@test.by", "userpass",authorities);
        Mockito.when(service.loadUserByUsername(Mockito.anyString()))
                .thenReturn(userDetails);
        //token = jwtTokenUtils.generateToken(userDetails);
        System.out.println("!!!!!userDetails "+userDetails);
    }

    @Test
    public void testSuccessToken() throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder ()
                .email("test@test.by")
                .password("test").build();

       // when(service.loadUserByUsername(request.getEmail())).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/authentication/")
                .header("AUTHORIZATION","Bearer "+token).with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
                //.andExpect(content().json(objectMapper.writeValueAsString(response))); //MockMvcResultMatchers.status().isOk()
    }


   /* public void registrateUser_whenCorrect_thenOk()(@RequestBody @Valid RegistrationRequest request) {
        log.info("FROM AuthControllerTest => User registration request : " + request.getEmail());
        authenticationService.registration(request);
    }*/


  /*  @Test
    //@WithUserDetails("user@test.by")
    public void authenticateUser_whenCorrect_thenOk() throws Exception {

        User user = UserGenerator.generateUser();
        Optional <User> optionalUser = Optional.ofNullable(user);
        org.springframework.security.core.userdetails.User securityUser = optionalUser.map(userDetails -> new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),
                Collections.singleton(optionalUser.get().getRole())
        )).orElseThrow(() -> new UsernameNotFoundException(user.getEmail()+ " not found"));

        AuthenticationRequest request = AuthenticationRequest.builder ()
                .email("user@test.by")
                .password("userpass").build();

        log.info("FROM AuthControllerTest => authenticateUser() : "+request.getEmail());
        //UserDetails user = service.loadUserByUsername(request.getEmail());
        when(service.loadUserByUsername(request.getEmail())).thenReturn(securityUser);
        System.out.println("!!!!!"+service.loadUserByUsername(request.getEmail()));
        System.out.println("!!!!!securityUser "+securityUser);

       // String jwt = jwtTokenUtils.generateToken(securityUser);
        //System.out.println("!!!!"+jwt);
        JwtResponse response = new JwtResponse(token);

        //when(jwtTokenUtils.generateToken(securityUser)).thenReturn(response.getToken());
        when (authenticationService.authentication(request)).thenReturn(response);
        System.out.println("!!!!"+response);

        mockMvc.perform(post("/auth/authentication/").with(csrf()) // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }*/

}