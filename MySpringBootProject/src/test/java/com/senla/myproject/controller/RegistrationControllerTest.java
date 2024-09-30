package com.senla.myproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.EntityMapper;
import com.senla.myproject.model.*;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = RegistrationController.class)
public class RegistrationControllerTest {

    @MockBean // объект добавляет в Contex в отличие от @Mock
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private EntityMapper entityMapper;

    @BeforeEach
    public void setUp(){
        entityMapper = new EntityMapper();
    }

    @Test
    public void testAddCarrierManager() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = entityMapper.managerToDTO(request);
        when(service.saveCarrierManager(response)).thenReturn(response);

        this.mockMvc.perform(post("/managers")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddCarriageRequest() throws Exception {
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = entityMapper.carriageRequestToDTO(request);
        when(service.saveOrder(response)).thenReturn(response);

        this.mockMvc.perform(post("/orders")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddCarrier() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = entityMapper.carrierToDTO(request);
        when(service.saveCarrier(response)).thenReturn(response);

        this.mockMvc.perform(post("/carriers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddFreightForwarder() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = entityMapper.forwarderToDTO(request);
        when(service.saveFreightForwarder(response)).thenReturn(response);

        this.mockMvc.perform(post("/forwarders")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddTruckPark() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = entityMapper.truckParkToDTO(request);
        when(service.saveTruckPark(response)).thenReturn(response);

        this.mockMvc.perform(post("/truck_parks")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testUpdateCarrierManager() throws Exception {

    }

    @Test
    public void testUpdateCarriageRequest() throws Exception {

    }

    @Test
    public void testUpdateCarrier() throws Exception {

    }

    @Test
    public void testUpdateFreightForwarder() throws Exception {

    }

    @Test
    public void testUpdateTruckPark() throws Exception {

    }

}