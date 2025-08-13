package com.senla.myproject.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.exceptions.NotFoundException;
import com.senla.myproject.exceptions.OrderNotValidException;
import com.senla.myproject.mapper.CarriageRequestMapper;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.mapper.CarrierMapper;
import com.senla.myproject.mapper.TruckParkMapper;
import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.model.Carrier;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.TruckPark;
import com.senla.myproject.service.FreightExchangeService;
import com.senla.myproject.util.CarriageRequestGenerator;
import com.senla.myproject.util.CarrierGenerator;
import com.senla.myproject.util.CarrierManagerGenerator;
import com.senla.myproject.util.TruckParkGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = CarrierManagerController.class)
@Slf4j // для логирования
@WithMockUser // тестрирование с аутентифицированным пользователем
public class CarrierManagerControllerTest {

    private final static Long ENTITY_ID = 1l;
    private final static String ORDER_NAME = "TestOrderName";

    @MockBean // объект добавляет в Spring ApplicationContext в отличие от @Mock => заменяет бин на мок в контексте
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void takeCarriageRequestByNameIsLike_whenCorrect_thenOk() throws Exception {
        log.info("FROM CarrierManagerUserControllerTest => TEST to take the Order with name: "+ORDER_NAME);
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);

        CarriageRequest request = CarriageRequestGenerator.generateOrder(); //request - запрос
        CarriageRequestDto response =  CarriageRequestMapper.INSTANSE.toDto(request);
        if (response.getValid()==true) {
            response.setValid(false);
            response.setManager(manager);
            manager.getOrders().add(CarriageRequestMapper.INSTANSE.toEntity(response));
        }
        when(service.findCarrierManagerByEmailIsLike("test@test.by")).thenReturn(managerDto);
        when(service.takeValidOrder(manager,ORDER_NAME)).thenReturn(response)
                .thenThrow(new OrderNotValidException("This order is not valid"));

        this.mockMvc.perform(get("/orders/take_order/"+ORDER_NAME).with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void  cancelCarriageRequestByNameIsLike_whenCorrect_thenOk() throws Exception{
        log.info("FROM CarrierManagerUserControllerTest => TEST to cancel the Order with name: "+ORDER_NAME);
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();

        CarriageRequest request = CarriageRequestGenerator.generateOrder(); //request - запрос
        request.setValid(false);
        manager.getOrders().add(request);

        CarriageRequestDto response =  CarriageRequestMapper.INSTANSE.toDto(request);
        Set<CarriageRequest> managerOrders = manager.getOrders();
        for (CarriageRequest order: managerOrders){
            if(order.equals(request)){
                response.setValid(true);
                response.setManager(null);
                manager.getOrders().remove(request);
            }
        }

        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        when(service.findCarrierManagerByEmailIsLike("test@test.by")).thenReturn(managerDto);
        when(service.cancelOrder(manager,ORDER_NAME)).thenReturn(response)
                .thenThrow(new NotFoundException("This manager doesn't have such order"));

        this.mockMvc.perform(get("/orders/cancel_order/"+ORDER_NAME).with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void updateCarrierManager_whenCorrect_thenOk() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        request.setId(2l);
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDto(request);

        log.info("FROM UserControllerTest => Request to UPDATE the CarrierManagers: "+response);
        when(service.saveCarrierManager(response)).thenReturn(response);

        this.mockMvc.perform(put("/managers").with(csrf())
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
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDto(request);

        when(service.deleteCarrierManagerById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/managers/" + ENTITY_ID).with(csrf())//без with(csrf()) - 403 Forbidden
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void updateAddCarrier_whenCorrect_thenOk() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        request.setId(2l);
        CarrierDto response = CarrierMapper.INSTANSE.toDto(request);

        log.info("FROM UserControllerTest => Request to UPDATE the Carrier: "+response);
        when(service.saveCarrier(response)).thenReturn(response);

        this.mockMvc.perform(put("/carriers").with(csrf()) //без with(csrf()) - 403 Forbidden
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
        CarrierDto response = CarrierMapper.INSTANSE.toDto(request);

        when(service.deleteCarrierById(ENTITY_ID)).thenReturn(response);

        this.mockMvc.perform(delete("/carriers/" + ENTITY_ID).with(csrf()) //без with(csrf()) - 403 Forbidden
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void updateAddTruckPark() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDto(request);

        log.info("FROM UserControllerTest => Request to UPDATE the TruckPark: "+response);
        when(service.saveTruckPark(response)).thenReturn(response);

        this.mockMvc.perform(put("/truck_parks").with(csrf())
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
        TruckParkDto response = TruckParkMapper.INSTANSE.toDto(request);

        when(service.deleteTruckParkById(ENTITY_ID)).thenReturn(response);

        mockMvc.perform(delete("/truck_parks/" + ENTITY_ID).with(csrf()) // HTTP Method = DELETE
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON) //Content-Type:"application/json;charset=UTF-8", Content-Length:"101"
                        .param("id", String.valueOf(ENTITY_ID))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }

    @Test
    public void testAddCarrierManager() throws Exception {
        CarrierManager request = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto response = CarrierManagerMapper.INSTANSE.toDto(request);

        log.info("FROM RegistrationControllerTest => Request to ADD the CarrierManager: "+response);
        when(service.saveCarrierManager(response)).thenReturn(response);

        this.mockMvc.perform(post("/managers").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddCarrier() throws Exception {
        Carrier request = CarrierGenerator.generateCarrier();
        CarrierDto response = CarrierMapper.INSTANSE.toDto(request);

        log.info("FROM RegistrationControllerTest => Request to ADD the Carrier: "+response);
        when(service.saveCarrier(response)).thenReturn(response);

        this.mockMvc.perform(post("/carriers").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddTruckPark() throws Exception {
        TruckPark request = TruckParkGenerator.generateTruckPark();
        TruckParkDto response = TruckParkMapper.INSTANSE.toDto(request);

        log.info("FROM RegistrationControllerTest => Request to ADD the TruckPark: "+response);
        when(service.saveTruckPark(response)).thenReturn(response);

        this.mockMvc.perform(post("/truck_parks").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}

