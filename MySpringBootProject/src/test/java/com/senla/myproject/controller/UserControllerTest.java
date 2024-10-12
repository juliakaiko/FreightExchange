package com.senla.myproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.*;
import com.senla.myproject.model.*;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.util.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

        log.info("FROM UserControllerTest => Request to GET the CarrierManager: "+response);
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
    public void getCarrierManager_whenIncorrect_thenThrowEntityNotFound() throws Exception {
        String message = "CarrierManager with id " + ENTITY_ID + " was not found";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorMessage = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), message);

        when(service.findCarrierManagerById(ENTITY_ID)).thenThrow(new EntityNotFoundException(message));

        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to GET the CarrierManager incorrectly: "+response);
        mockMvc.perform(get("/managers/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(errorMessage)); // jsonPath() проверяет отд-ные поля
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

        log.info("FROM UserControllerTest => Request to GET ALL CarrierManagers: "+managerList);
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
    public void updateCarrierManager_whenCorrect_thenOk() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        request.setId(2l);
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to UPDATE the CarrierManagers: "+response);
        when(service.saveCarrierManager(response)).thenReturn(response);

        this.mockMvc.perform(put("/managers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void deleteCarrierManager_whenCorrect_thenOk() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDTO(request);

        //when(service.deleteCarrierManagerById(ENTITY_ID)).thenReturn(response);
        when(service.findCarrierManagerById(ENTITY_ID)).thenReturn(response);

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
    public void getCarriageRequest_whenIncorrect_thenThrowEntityNotFound() throws Exception {
        String message = "CarriageRequest with id " + ENTITY_ID + " was not found";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorMessage = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), message);

        when(service.findOrderById(ENTITY_ID)).thenThrow(new EntityNotFoundException(message));

        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to GET the Order incorrectly: "+response);
        mockMvc.perform(get("/orders/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(errorMessage)); // jsonPath() проверяет отд-ные поля
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
    public void updateCarriageRequest_whenCorrect_thenOk() throws Exception {
        CarriageRequest request = CarriageRequestGenerator.generateOrder();
        request.setId(2l);
        CarriageRequestDto response = CarriageRequestMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Order: "+response);
        when(service.saveOrder(response)).thenReturn(response);

        this.mockMvc.perform(put("/orders")
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
    public void getCarrier_whenIncorrect_thenThrowEntityNotFound() throws Exception {
        String message = "Carrier with id " + ENTITY_ID + " was not found";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorMessage = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), message);

        when(service.findCarrierById(ENTITY_ID)).thenThrow(new EntityNotFoundException(message));

        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = CarrierMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to GET the Carrier incorrectly: "+response);
        mockMvc.perform(get("/carriers/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(errorMessage)); // jsonPath() проверяет отд-ные поля
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

    @Test
    public void updateAddCarrier_whenCorrect_thenOk() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        request.setId(2l);
        CarrierDto response = CarrierMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Carrier: "+response);
        when(service.saveCarrier(response)).thenReturn(response);

        this.mockMvc.perform(put("/carriers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void deleteCarrier_whenCorrect_thenOk() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = CarrierMapper.INSTANSE.toDTO(request);

        //when(service.deleteCarrierById(ENTITY_ID)).thenReturn(response);
        when(service.findCarrierById(ENTITY_ID)).thenReturn(response);

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
    public void getFreightForwarder_whenIncorrect_thenThrowEntityNotFound() throws Exception {
        String message = "FreightForwarder with id " + ENTITY_ID + " was not found";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorMessage = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), message);

        when(service.findFreightForwarderById(ENTITY_ID)).thenThrow(new EntityNotFoundException(message));

        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to GET the FreightForwarder incorrectly: "+response);
        mockMvc.perform(get("/forwarders/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(errorMessage)); // jsonPath() проверяет отд-ные поля
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

    @Test
    public void updateAddFreightForwarder() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Forwarder: "+response);
        when(service.saveFreightForwarder(response)).thenReturn(response);

        this.mockMvc.perform(put("/forwarders")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test ///!!!!
    public void deleteFreightForwarder_whenCorrect_thenOk() throws Exception {
        FreightForwarder request = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto response = FreightForwarderMapper.INSTANSE.toDTO(request);

        //when(service.deleteFreightForwarderById(ENTITY_ID)).thenReturn(response);
        when(service.findFreightForwarderById(ENTITY_ID)).thenReturn(response);

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
    public void getTruckPark_whenIncorrect_thenThrowEntityNotFound() throws Exception {
        String message = "TruckPark with id " + ENTITY_ID + " was not found";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorMessage = String.format("%s %s", LocalDateTime.now().format(dateTimeFormatter), message);

        when(service.findTruckParkById(ENTITY_ID)).thenThrow(new EntityNotFoundException(message));

        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to GET the TruckPark incorrectly: "+response);
        mockMvc.perform(get("/truck_parks/" + ENTITY_ID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(errorMessage)); // jsonPath() проверяет отд-ные поля
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
    public void updateAddTruckPark() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDTO(request);

        log.info("FROM UserControllerTest => Request to UPDATE the TruckPark: "+response);
        when(service.saveTruckPark(response)).thenReturn(response);

        this.mockMvc.perform(put("/truck_parks")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void deleteTruckPark_whenCorrect_thenOk() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDTO(request);

        //when(service.deleteTruckParkById(ENTITY_ID)).thenReturn(response);
        when(service.findTruckParkById(ENTITY_ID)).thenReturn(response);

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