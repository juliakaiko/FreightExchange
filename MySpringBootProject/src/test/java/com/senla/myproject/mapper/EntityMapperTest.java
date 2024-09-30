package com.senla.myproject.mapper;

import com.senla.myproject.dto.*;
import com.senla.myproject.model.*;
import com.senla.myproject.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.senla.myproject.mapper.EntityMapper;

public class EntityMapperTest {

    private static EntityMapper mapper; // = new EntityMapper();

    @BeforeAll
    public static void setUp(){
        mapper = new EntityMapper();
    }

    @Test
    public void forwarderToDTO() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = mapper.forwarderToDTO(forwarder);
        assertEquals(forwarder.getId(), forwarderDto.getId());
        assertEquals(forwarder.getEmail(), forwarderDto.getEmail());
        assertEquals(forwarder.getPassword(),forwarderDto.getPassword());
        assertEquals(forwarder.getSurName(), forwarderDto.getSurName());
        assertEquals(forwarder.getFirstName(), forwarderDto.getFirstName());
        assertEquals(forwarder.getOrders(), forwarderDto.getOrders());
    }

    @Test
    public void forwarderDtoToEntity() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = mapper.forwarderToDTO(forwarder);
        assertEquals(forwarderDto.getId(), forwarder.getId());
        assertEquals(forwarderDto.getEmail(), forwarder.getEmail());
        assertEquals(forwarderDto.getPassword(), forwarder.getPassword());
        assertEquals(forwarderDto.getSurName(), forwarder.getSurName());
        assertEquals(forwarderDto.getFirstName(), forwarder.getFirstName());
        assertEquals(forwarderDto.getOrders(), forwarder.getOrders());
    }

    @Test
    public void managerToDTO() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = mapper.managerToDTO(manager);
        assertEquals(manager.getId(), managerDto.getId());
        assertEquals(manager.getEmail(), managerDto.getEmail());
        assertEquals(manager.getPassword(), managerDto.getPassword());
        assertEquals(manager.getSurName(), managerDto.getSurName());
        assertEquals(manager.getFirstName(), managerDto.getFirstName());
        assertEquals(manager.getCarriers(), managerDto.getCarriers());
        assertEquals(manager.getOrders(), managerDto.getOrders());
    }

    @Test
    public void managerDtoToEntity() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = mapper.managerToDTO(manager);
        assertEquals(managerDto.getId(), manager.getId());
        assertEquals(managerDto.getEmail(), manager.getEmail());
        assertEquals(managerDto.getPassword(), manager.getPassword());
        assertEquals(managerDto.getSurName(), manager.getSurName());
        assertEquals(managerDto.getFirstName(), manager.getFirstName());
        assertEquals(managerDto.getCarriers(), manager.getCarriers());
        assertEquals(managerDto.getOrders(), manager.getOrders());
    }

    @Test
    public void carrierToDTO() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = mapper.carrierToDTO(carrier);
        assertEquals(carrier.getId(), carrierDto.getId());
        assertEquals(carrier.getName(), carrierDto.getName());
        assertEquals(carrier.getAddress(), carrierDto.getAddress());
        assertEquals(carrier.getPark(), carrierDto.getPark());
        assertEquals(carrier.getCarrierManagers(), carrierDto.getCarrierManagers());
    }

    @Test
    public void carrierDtoToEntity() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = mapper.carrierToDTO(carrier);
        assertEquals(carrierDto.getId(), carrier.getId());
        assertEquals(carrierDto.getName(), carrier.getName());
        assertEquals(carrierDto.getAddress(), carrier.getAddress());
        assertEquals(carrierDto.getPark(), carrier.getPark());
        assertEquals(carrierDto.getCarrierManagers(), carrier.getCarrierManagers());
    }

    @Test
    public void carriageRequestToDTO() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = mapper.carriageRequestToDTO(order);
        assertEquals(order.getId(), orderDto.getId());
        assertEquals(order.getOrderName(), orderDto.getOrderName());
        assertEquals(order.getStartPoint(), orderDto.getStartPoint());
        assertEquals(order.getFinishPoint(), orderDto.getFinishPoint());
        assertEquals(order.getCargo(), orderDto.getCargo());
        assertEquals(order.getFreight(), orderDto.getFreight());
        assertEquals(order.isValid(), orderDto.isValid());
        assertEquals(order.getManager(), orderDto.getManager());
        assertEquals(order.getForwarder(), orderDto.getForwarder());
    }

    @Test
    public void carriageRequestDtoToEntity() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = mapper.carriageRequestToDTO(order);
        assertEquals(orderDto.getId(),order.getId());
        assertEquals(orderDto.getOrderName(), order.getOrderName());
        assertEquals(orderDto.getStartPoint(), order.getStartPoint());
        assertEquals(orderDto.getFinishPoint(), order.getFinishPoint());
        assertEquals(orderDto.getCargo(), order.getCargo());
        assertEquals(orderDto.getFreight(), order.getFreight());
        assertEquals(orderDto.isValid(), order.isValid());
        assertEquals(orderDto.getManager(), order.getManager());
        assertEquals(orderDto.getForwarder(), order.getForwarder());
    }

    @Test
    public void truckParkToDTO() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = mapper.truckParkToDTO(park);
        assertEquals(park.getId(), parkDto.getId());
        assertEquals(park.getTrucksLoadCapacity(), parkDto.getTrucksLoadCapacity());
        assertEquals(park.getCarrier(), parkDto.getCarrier());
    }

    @Test
    public void truckParkDtoToEntity() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = mapper.truckParkToDTO(park);
        assertEquals(parkDto.getId(),park.getId());
        assertEquals(parkDto.getTrucksLoadCapacity(), park.getTrucksLoadCapacity());
        assertEquals(parkDto.getCarrier(), park.getCarrier());
    }
}