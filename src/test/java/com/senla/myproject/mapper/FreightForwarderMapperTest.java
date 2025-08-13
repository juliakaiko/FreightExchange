package com.senla.myproject.mapper;

import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.util.FreightForwarderGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreightForwarderMapperTest {

    @Test
    public void forwarderToDTO_whenOk_thenMapFieldsCorrectly() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);
        assertEquals(forwarder.getId(), forwarderDto.getId());
        assertEquals(forwarder.getEmail(), forwarderDto.getEmail());
        assertEquals(forwarder.getPassword(),forwarderDto.getPassword());
        assertEquals(forwarder.getSurName(), forwarderDto.getSurName());
        assertEquals(forwarder.getFirstName(), forwarderDto.getFirstName());
        assertEquals(forwarder.getOrders(), forwarderDto.getOrders());
        assertEquals(forwarder.getRole(), forwarderDto.getRole());
    }

    @Test
    public void forwarderDtoToEntity_whenOk_thenMapFieldsCorrectly() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);
        forwarder = FreightForwarderMapper.INSTANSE.toEntity(forwarderDto);
        assertEquals(forwarderDto.getId(), forwarder.getId());
        assertEquals(forwarderDto.getEmail(), forwarder.getEmail());
        assertEquals(forwarderDto.getPassword(), forwarder.getPassword());
        assertEquals(forwarderDto.getSurName(), forwarder.getSurName());
        assertEquals(forwarderDto.getFirstName(), forwarder.getFirstName());
        assertEquals(forwarderDto.getOrders(), forwarder.getOrders());
        assertEquals(forwarderDto.getRole(), forwarder.getRole());
    }
}