package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.model.CarriageRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarriageRequestMapper {

    CarriageRequestMapper INSTANSE = Mappers.getMapper(CarriageRequestMapper.class);

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "orderName", source = "order.orderName")
    @Mapping(target = "startPoint", source = "order.startPoint")
    @Mapping(target = "finishPoint", source = "order.finishPoint")
    @Mapping(target = "cargo", source = "order.cargo")
    @Mapping(target = "freight", source = "order.freight")
    @Mapping(target = "valid", source = "order.valid")
    @Mapping(target = "forwarder", source = "order.forwarder")
    @Mapping(target = "manager", source = "order.manager")
    CarriageRequestDto toDTO (CarriageRequest order);

    @InheritInverseConfiguration// преобразует обратно DTO в Entity
    CarriageRequest toEntity (CarriageRequestDto requestDto);
}
