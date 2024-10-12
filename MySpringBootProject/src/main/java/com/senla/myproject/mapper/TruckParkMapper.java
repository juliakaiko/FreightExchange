package com.senla.myproject.mapper;

import com.senla.myproject.dto.TruckParkDto;
import com.senla.myproject.model.TruckPark;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TruckParkMapper {

    TruckParkMapper INSTANSE = Mappers.getMapper(TruckParkMapper.class);

    @Mapping(target = "id", source = "park.id")
    @Mapping(target = "trucksNum", source = "park.trucksNum")
    @Mapping(target = "trucksLoadCapacity", source = "park.trucksLoadCapacity")
    @Mapping(target = "carrier", source = "park.carrier")
    TruckParkDto toDTO (TruckPark park);

    @InheritInverseConfiguration// преобразует обратно DTO в Entity
    TruckPark toEntity (TruckParkDto parkDto);
}
