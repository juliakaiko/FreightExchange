package com.senla.myproject.mapper;

import com.senla.myproject.dto.FreightForwarderDto;
import com.senla.myproject.model.FreightForwarder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FreightForwarderMapper {

    FreightForwarderMapper INSTANSE = Mappers.getMapper(FreightForwarderMapper.class);
    FreightForwarderDto toDTO (FreightForwarder forwarder);
}
