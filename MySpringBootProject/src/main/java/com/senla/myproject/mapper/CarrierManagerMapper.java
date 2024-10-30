package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarrierManagerDto;
import com.senla.myproject.model.CarrierManager;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarrierManagerMapper {

    CarrierManagerMapper INSTANSE = Mappers.getMapper(CarrierManagerMapper.class);

    @Mapping(target = "id", source = "manager.id")
    @Mapping(target = "email", source = "manager.email")
    @Mapping(target = "password", source = "manager.password")
    @Mapping(target = "firstName", source = "manager.firstName")
    @Mapping(target = "surName", source = "manager.surName")
    @Mapping(target = "carriers", source = "manager.carriers")
    @Mapping(target = "orders", source = "manager.orders")
    CarrierManagerDto toDto(CarrierManager manager);

    @InheritInverseConfiguration // преобразует обратно DTO в Entity
    CarrierManager toEntity (CarrierManagerDto managerDto);
}
