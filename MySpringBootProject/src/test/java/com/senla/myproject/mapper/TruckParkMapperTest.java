package com.senla.myproject.mapper;

import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.model.TruckPark;
import com.senla.myproject.util.TruckParkGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TruckParkMapperTest {

    @Test
    public void truckParkToDTO_whenOk_thenMapFieldsCorrectly() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = TruckParkMapper.INSTANSE.toDto(park);
        assertEquals(park.getId(), parkDto.getId());
        assertEquals(park.getTrucksLoadCapacity(), parkDto.getTrucksLoadCapacity());
        assertEquals(park.getCarrier(), parkDto.getCarrier());
    }

    @Test
    public void truckParkDtoToEntity_whenOk_thenMapFieldsCorrectly() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = TruckParkMapper.INSTANSE.toDto(park);
        park = TruckParkMapper.INSTANSE.toEntity(parkDto);
        assertEquals(parkDto.getId(),park.getId());
        assertEquals(parkDto.getTrucksLoadCapacity(), park.getTrucksLoadCapacity());
        assertEquals(parkDto.getCarrier(), park.getCarrier());
    }
}