package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.util.CarrierManagerGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrierManagerMapperTest {

    @Test
    public void managerToDTO_whenOk_thenMapFieldsCorrectly() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDTO(manager);
        assertEquals(manager.getId(), managerDto.getId());
        assertEquals(manager.getEmail(), managerDto.getEmail());
        assertEquals(manager.getPassword(), managerDto.getPassword());
        assertEquals(manager.getSurName(), managerDto.getSurName());
        assertEquals(manager.getFirstName(), managerDto.getFirstName());
        assertEquals(manager.getCarriers(), managerDto.getCarriers());
        assertEquals(manager.getOrders(), managerDto.getOrders());
    }

    @Test
    public void managerDtoToEntity_whenOk_thenMapFieldsCorrectly() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDTO(manager);
        manager = CarrierManagerMapper.INSTANSE.toEntity(managerDto);
        assertEquals(managerDto.getId(), manager.getId());
        assertEquals(managerDto.getEmail(), manager.getEmail());
        assertEquals(managerDto.getPassword(), manager.getPassword());
        assertEquals(managerDto.getSurName(), manager.getSurName());
        assertEquals(managerDto.getFirstName(), manager.getFirstName());
        assertEquals(managerDto.getCarriers(), manager.getCarriers());
        assertEquals(managerDto.getOrders(), manager.getOrders());
    }
}