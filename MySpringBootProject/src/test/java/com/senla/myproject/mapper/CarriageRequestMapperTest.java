package com.senla.myproject.mapper;

import com.senla.myproject.dto.CarriageRequestDto;
import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.util.CarriageRequestGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarriageRequestMapperTest {

    @Test
    public void carriageRequestToDTO_whenOk_thenMapFieldsCorrectly() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDTO(order);
        assertEquals(order.getId(), orderDto.getId());
        assertEquals(order.getOrderName(), orderDto.getOrderName());
        assertEquals(order.getStartPoint(), orderDto.getStartPoint());
        assertEquals(order.getFinishPoint(), orderDto.getFinishPoint());
        assertEquals(order.getCargo(), orderDto.getCargo());
        assertEquals(order.getFreight(), orderDto.getFreight());
        assertEquals(order.getValid(), orderDto.getValid());
        assertEquals(order.getManager(), orderDto.getManager());
        assertEquals(order.getForwarder(), orderDto.getForwarder());
    }

    @Test
    public void carriageRequestDtoToEntity_whenOk_thenMapFieldsCorrectly() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDTO(order);
        order=CarriageRequestMapper.INSTANSE.toEntity(orderDto);
        assertEquals(orderDto.getId(),order.getId());
        assertEquals(orderDto.getOrderName(), order.getOrderName());
        assertEquals(orderDto.getStartPoint(), order.getStartPoint());
        assertEquals(orderDto.getFinishPoint(), order.getFinishPoint());
        assertEquals(orderDto.getCargo(), order.getCargo());
        assertEquals(orderDto.getFreight(), order.getFreight());
        assertEquals(orderDto.getValid(), order.getValid());
        assertEquals(orderDto.getManager(), order.getManager());
        assertEquals(orderDto.getForwarder(), order.getForwarder());
    }
}