package com.senla.myproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.mapper.CarrierManagerMapper;
import com.senla.myproject.mapper.CarrierMapper;
import com.senla.myproject.mapper.TruckParkMapper;
import com.senla.myproject.model.Carrier;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.TruckPark;
import com.senla.myproject.service.FreightExchangeService;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = CarrierManagerRegistrationController.class)
@Slf4j // для логирования
@WithMockUser // тестрирование с аутентифицированным пользователем
public class CarrierManagerRegistrationControllerTest {

    @MockBean // объект добавляет в Spring ApplicationContext в отличие от @Mock => заменяет бин на мок в контексте
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
