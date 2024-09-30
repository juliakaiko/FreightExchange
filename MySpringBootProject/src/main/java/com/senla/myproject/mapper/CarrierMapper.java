package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.model.Carrier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarrierMapper {

    CarrierMapper INSTANSE = Mappers.getMapper(CarrierMapper.class);
    CarrierDto toDTO (Carrier carrier);

}
