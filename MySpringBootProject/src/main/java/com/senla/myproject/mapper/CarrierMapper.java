package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarrierDto;
import com.senla.myproject.model.Carrier;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface CarrierMapper {

    CarrierMapper INSTANSE = Mappers.getMapper(CarrierMapper.class);

    @Mapping(target = "id", source = "carrier.id")
    @Mapping(target = "name", source = "carrier.name")
    @Mapping(target = "address", source = "carrier.address")
    @Mapping(target = "park", source = "carrier.park")
    CarrierDto toDTO (Carrier carrier);

    @InheritInverseConfiguration // преобразует обратно DTO в Entity
    Carrier toEntity (CarrierDto carrierDto);

}
