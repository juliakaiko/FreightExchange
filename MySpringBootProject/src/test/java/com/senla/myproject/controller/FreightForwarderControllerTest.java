package com.senla.myproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.mapper.CarriageRequestMapper;
import com.senla.myproject.mapper.FreightForwarderMapper;
import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.util.CarriageRequestGenerator;
import com.senla.myproject.util.FreightForwarderGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FreightForwarderController.class)
@Slf4j // для логирования
@WithMockUser
public class FreightForwarderControllerTest {

    private final static Long ENTITY_ID = 1l;
    @MockBean // объект добавляет в Contex в отличие от @Mock
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void updateAddFreightForwarder() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDto(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Forwarder: "+response);
        when(service.saveFreightForwarder(response)).thenReturn(response);

        this.mockMvc.perform(put("/forwarders").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void deleteFreightForwarder_whenCorrect_thenOk() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDto(request);

        when(service.deleteFreightForwarderById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/forwarders/" + ENTITY_ID).with(csrf())// HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void testAddCarriageRequest() throws Exception {
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDto(request);

        log.info("FROM RegistrationControllerTest => Request to ADD the Order: "+response);
        when(service.saveOrder(response)).thenReturn(response);

        this.mockMvc.perform(post("/orders").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void updateCarriageRequest_whenCorrect_thenOk() throws Exception {
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        request.setId(2l);
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDto(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Order: "+response);
        when(service.saveOrder(response)).thenReturn(response);

        this.mockMvc.perform(put("/orders").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void deleteCarriageRequest_whenCorrect_thenOk() throws Exception{
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDto(request);

        when(service.deleteOrderById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/orders/" + ENTITY_ID).with(csrf()) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    /*@Test
    public void testAddFreightForwarder() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDto(request);

        log.info("FROM RegistrationControllerTest => Request to ADD the Forwarder: "+response);
        when(service.saveFreightForwarder(response)).thenReturn(response);

        this.mockMvc.perform(post("/forwarders").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }*/
}
