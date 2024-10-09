package com.senla.myproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.*;
import com.senla.myproject.model.*;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private final static Long ENTITY_ID = 1l;
    @MockBean // объект добавляет в Contex в отличие от @Mock
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCarrierManager_whenCorrect_thenOk() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDTO(request);
        when(service.findCarrierManagerById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(get("/managers/" + ENTITY_ID) // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getAllCarrierManagers_whenCorrect_thenOk() throws Exception{
        CarrierManager manager1 = CarrierManagerGenerator.generateCarrierManager();
        CarrierManager manager2 = CarrierManagerGenerator.generateCarrierManager();
        manager2.setId(2l); manager2.setEmail("test2@test.by");
        CarrierManager manager3 = CarrierManagerGenerator.generateCarrierManager();
        manager3.setId(3l); manager3.setEmail("test3@test.by");
        List<CarrierManager> managerList = new ArrayList<>();
        managerList.add(manager1);
        managerList.add(manager2);
        managerList.add(manager3);
        when(service.findAllCarrierManagers()).thenReturn(managerList);

        mockMvc.perform(get("/managers") // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(managerList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(managerList)))
                .andDo(print());
    }

    @Test
    public void deleteCarrierManager_whenCorrect_thenOk() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDTO(request);

        when(service.deleteCarrierManagerById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/managers/" + ENTITY_ID) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    //Order
    @Test
    public void getCarriageRequest_whenCorrect_thenOk() throws Exception{
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDTO(request);
        when(service.findOrderById(ENTITY_ID)).thenReturn(response);

        this.mockMvc.perform(get("/orders/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getAllCarriageRequests_whenCorrect_thenOk() throws Exception{
        CarriageRequest order1 = CarriageRequestGenerator.generateOrder();
        CarriageRequest order2 = CarriageRequestGenerator.generateOrder();
        order2.setId(2l);
        CarriageRequest order3 = CarriageRequestGenerator.generateOrder();
        order3.setId(3l);
        List <CarriageRequest> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        // Стаббинг: определение поведения
        when(service.findAllOrders()).thenReturn(orderList);

        mockMvc.perform(get("/orders") // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(orderList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderList)))
                .andDo(print());
    }

    @Test
    public void deleteCarriageRequest_whenCorrect_thenOk() throws Exception{
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDTO(request);
        when(service.findOrderById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/orders/" + ENTITY_ID) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    //Carrier
    @Test
    public void getCarrier_whenCorrect_thenOk() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = CarrierMapper.INSTANSE.toDTO(request);

        when(service.findCarrierById(ENTITY_ID)).thenReturn(response);

        this.mockMvc.perform(get("/carriers/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getAllCarriers_whenCorrect_thenOk() throws Exception {
        Carrier carrier1 = CarrierGenerator.generateCarrier();
        Carrier carrier2 = CarrierGenerator.generateCarrier();
        carrier2.setId(2l); carrier2.setAddress("carrier1_address");
        Carrier carrier3 = CarrierGenerator.generateCarrier();
        carrier3.setId(3l); carrier3.setAddress("carrier3_address");
        List <Carrier> carrierList = new ArrayList<>();
        carrierList.add(carrier1);
        carrierList.add(carrier2);
        carrierList.add(carrier3);
        // Стаббинг: определение поведения
        when(service.findAllCarriers()).thenReturn(carrierList);

        mockMvc.perform(get("/carriers") // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(carrierList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carrierList)))
                .andDo(print());
    }

    @Test//!!!
    public void deleteCarrier_whenCorrect_thenOk() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = CarrierMapper.INSTANSE.toDTO(request);

        when(service.deleteCarrierById(ENTITY_ID)).thenReturn(response);

        this.mockMvc.perform(delete("/carriers/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    //FreightForwarder
    @Test
    public void getFreightForwarder_whenCorrect_thenOk() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDTO(request);

        when(service.findFreightForwarderById(ENTITY_ID)).thenReturn(response);

        this.mockMvc.perform(get("/forwarders/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getAlFreightForwarders_whenCorrect_thenOk() throws Exception {
        FreightForwarder forwarder1 = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarder forwarder2 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder2.setId(2l); forwarder2.setEmail("test2@test.by");
        FreightForwarder forwarder3 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder3.setId(3l); forwarder3.setEmail("test3@test.by");
        List <FreightForwarder> forwarderList = new ArrayList<>();
        forwarderList.add(forwarder1);
        forwarderList.add(forwarder2);
        forwarderList.add(forwarder3);
        // Стаббинг: определение поведения
        when(service.findAllForwarders()).thenReturn(forwarderList);

        mockMvc.perform(get("/forwarders") // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(forwarderList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(forwarderList)))
                .andDo(print());
    }

    @Test ///!!!!
    public void deleteFreightForwarder_whenCorrect_thenOk() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDTO(request);

        when(service.deleteFreightForwarderById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/forwarders/" + ENTITY_ID) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    //TruckPark
    @Test
    public void getTruckPark_whenCorrect_thenOk() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDTO(request);

        when(service.findTruckParkById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(get("/truck_parks/" + ENTITY_ID) // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void getAllTruckParks_whenCorrect_thenOk() throws Exception {
        TruckPark park1 = TruckParkGenerator.generateTruckPark();
        TruckPark park2 = TruckParkGenerator.generateTruckPark();
        park2.setId(2l);
        TruckPark park3 = TruckParkGenerator.generateTruckPark();
        park3.setId(3l);
        List <TruckPark> parkList = new ArrayList<>();
        parkList.add(park1);
        parkList.add(park2);
        parkList.add(park3);
        // Стаббинг: определение поведения
        when(service.findAllTruckParks()).thenReturn(parkList);

        mockMvc.perform(get("/truck_parks") // HTTP Method = GET
                        .content(objectMapper.writeValueAsString(parkList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(parkList)))
                .andDo(print());
    }

    @Test
    public void deleteTruckPark_whenCorrect_thenOk() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDTO(request);

        when(service.deleteTruckParkById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/truck_parks/" + ENTITY_ID) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}