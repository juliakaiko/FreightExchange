package com.senla.myproject.mapper;

import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.model.FreightForwarder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface FreightForwarderMapper {

    FreightForwarderMapper INSTANSE = Mappers.getMapper(FreightForwarderMapper.class);

    @Mapping(target = "id", source = "forwarder.id")
    @Mapping(target = "email", source = "forwarder.email")
    @Mapping(target = "password", source = "forwarder.password")
    @Mapping(target = "firstName", source = "forwarder.firstName")
    @Mapping(target = "surName", source = "forwarder.surName")
    @Mapping(target = "orders", source = "forwarder.orders")
    FreightForwarderDto toDto(FreightForwarder forwarder);

    @InheritInverseConfiguration// преобразует обратно DTO в Entity
    FreightForwarder toEntity (FreightForwarderDto forwarderDto);
}
