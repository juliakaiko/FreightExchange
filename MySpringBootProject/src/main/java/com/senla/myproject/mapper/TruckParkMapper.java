package com.senla.myproject.mapper;

import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.model.TruckPark;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TruckParkMapper {

    TruckParkMapper INSTANSE = Mappers.getMapper(TruckParkMapper.class);
    TruckParkDto toDTO (TruckPark park);
}
