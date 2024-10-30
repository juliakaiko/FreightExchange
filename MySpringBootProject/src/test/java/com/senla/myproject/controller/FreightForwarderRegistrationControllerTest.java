package com.senla.myproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.mapper.FreightForwarderMapper;
import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.service.FreightExchangeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FreightForwarderRegistrationController.class)
@Slf4j // для логирования
@WithMockUser// тестрирование с аутентифицированным пользователем
public class FreightForwarderRegistrationControllerTest {

    @MockBean // объект добавляет в Contex в отличие от @Mock
    private FreightExchangeService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
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
    }
}
