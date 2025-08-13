package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.model.Carrier;
import com.senla.myproject.util.CarrierGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrierMapperTest {

    @Test
    public void carrierToDTO_whenOk_thenMapFieldsCorrectly() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = CarrierMapper.INSTANSE.toDto(carrier);
        assertEquals(carrier.getId(), carrierDto.getId());
        assertEquals(carrier.getName(), carrierDto.getName());
        assertEquals(carrier.getAddress(), carrierDto.getAddress());
        assertEquals(carrier.getPark(), carrierDto.getPark());
        assertEquals(carrier.getCarrierManagers(), carrierDto.getCarrierManagers());
    }

    @Test
    public void carrierDtoToEntity_whenOk_thenMapFieldsCorrectly() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = CarrierMapper.INSTANSE.toDto(carrier);
        carrier = CarrierMapper.INSTANSE.toEntity(carrierDto);
        assertEquals(carrierDto.getId(), carrier.getId());
        assertEquals(carrierDto.getName(), carrier.getName());
        assertEquals(carrierDto.getAddress(), carrier.getAddress());
        assertEquals(carrierDto.getPark(), carrier.getPark());
        assertEquals(carrierDto.getCarrierManagers(), carrier.getCarrierManagers());
    }
}