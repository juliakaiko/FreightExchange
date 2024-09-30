package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.model.CarriageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarriageRequestMapper {

    CarriageRequestMapper INSTANSE = Mappers.getMapper(CarriageRequestMapper.class);
    CarriageRequestDto toDTO (CarriageRequest order);
}
