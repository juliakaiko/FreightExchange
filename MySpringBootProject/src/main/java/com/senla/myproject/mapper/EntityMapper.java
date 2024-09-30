package com.senla.myproject.mapper;

import com.senla.myproject.model.*;
import com.senla.myproject.dto.*;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public FreightForwarderDto forwarderToDTO(FreightForwarder forwarder) {
        FreightForwarderDto dto = new FreightForwarderDto();
        if(forwarder.getId()!=null)
            dto.setId(forwarder.getId());
        dto.setEmail(forwarder.getEmail());
        dto.setPassword(forwarder.getPassword());
        dto.setFirstName(forwarder.getFirstName());
        dto.setSurName(forwarder.getSurName());
        dto.setOrders(forwarder.getOrders());
        return dto;
    }

    public FreightForwarder forwarderDtoToEntity(FreightForwarderDto forwarderDTO) {
        FreightForwarder forwarder = new FreightForwarder();
        forwarder.setId(forwarderDTO.getId());
        forwarder.setEmail(forwarderDTO.getEmail());
        forwarder.setPassword(forwarderDTO.getPassword());
        forwarder.setFirstName(forwarderDTO.getFirstName());
        forwarder.setSurName(forwarderDTO.getSurName());
        forwarder.setOrders(forwarder.getOrders());
        return forwarder;
    }

    public CarrierManagerDto managerToDTO(CarrierManager manager) {
        CarrierManagerDto dto = new CarrierManagerDto();
        if(manager.getId()!=null)
            dto.setId(manager.getId());
        dto.setEmail(manager.getEmail());
        dto.setPassword(manager.getPassword());
        dto.setFirstName(manager.getFirstName());
        dto.setSurName(manager.getSurName());
        dto.setCarriers(manager.getCarriers());
        dto.setOrders(manager.getOrders());
        return dto;
    }

    public CarrierManager managerDtoToEntity(CarrierManagerDto managerDTO) {
        CarrierManager manager = new CarrierManager();
        manager.setId(managerDTO.getId());
        manager.setEmail(managerDTO.getEmail());
        manager.setPassword(managerDTO.getPassword());
        manager.setFirstName(managerDTO.getFirstName());
        manager.setSurName(managerDTO.getSurName());
        manager.setCarriers(managerDTO.getCarriers());
        manager.setOrders(managerDTO.getOrders());
        return manager;
    }

    public CarrierDto carrierToDTO(Carrier carrier) {
        CarrierDto dto = new CarrierDto();
        if (carrier.getId()!=null)
            dto.setId(carrier.getId());
        dto.setName(carrier.getName());
        dto.setAddress(carrier.getAddress());
        dto.setCarrierManagers(carrier.getCarrierManagers());
        dto.setPark(carrier.getPark());
        return dto;
    }

    public Carrier carrierDtoToEntity(CarrierDto carrierDto) {
        Carrier carrier = new Carrier();
        carrier.setId(carrierDto.getId());
        carrier.setName(carrierDto.getName());
        carrier.setAddress(carrierDto.getAddress());
        carrier.setCarrierManagers(carrierDto.getCarrierManagers());
        carrier.setPark(carrierDto.getPark());
        return carrier;
    }

    public CarriageRequestDto carriageRequestToDTO(CarriageRequest order) {
        CarriageRequestDto dto = new CarriageRequestDto();
        if (order.getId()!=null)
            dto.setId(order.getId());
        dto.setOrderName(order.getOrderName());
        dto.setStartPoint(order.getStartPoint());
        dto.setFinishPoint(order.getFinishPoint());
        dto.setCargo(order.getCargo());
        dto.setFreight(order.getFreight());
        dto.setValid(order.isValid());
        dto.setManager(order.getManager());
        dto.setForwarder(order.getForwarder());
        return dto;
    }

    public CarriageRequest carriageRequestDtoToEntity(CarriageRequestDto carriageRequestDTO) {
        CarriageRequest order = new CarriageRequest();
        order.setId(carriageRequestDTO.getId());
        order.setOrderName(carriageRequestDTO.getOrderName());
        order.setStartPoint(carriageRequestDTO.getStartPoint());
        order.setFinishPoint(carriageRequestDTO.getFinishPoint());
        order.setCargo(carriageRequestDTO.getCargo());
        order.setFreight(carriageRequestDTO.getFreight());
        order.setValid(carriageRequestDTO.isValid());
        order.setManager(carriageRequestDTO.getManager());// !!!
        order.setForwarder(carriageRequestDTO.getForwarder());//!!!
        return order;
    }

    public TruckParkDto truckParkToDTO(TruckPark park) {
        TruckParkDto dto = new TruckParkDto();
        if(park.getId()!=null)
            dto.setId(park.getId());
            //dto.setId(park.getCarrier().getId());
        dto.setTrucksLoadCapacity(park.getTrucksLoadCapacity());
        dto.setTrucksNum(park.getTrucksNum());
        dto.setCarrier(park.getCarrier());
        return dto;
    }

    public TruckPark truckParkDtoToEntity(TruckParkDto parkDTO) {
        TruckPark  park = new TruckPark();
        park.setId(parkDTO.getId());
        park.setTrucksLoadCapacity(parkDTO.getTrucksLoadCapacity());
        park.setTrucksNum(parkDTO.getTrucksNum());
        park.setCarrier(parkDTO.getCarrier());//!!!!
        return park;
    }

/*    public UserDTO userToDTO(User user) {
        var dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setSurName(user.getSurName());
        return dto;
    }

    public User userDTOToEntity(UserDTO userDTO) {
        var user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setSurName(userDTO.getSurName());
        return user;
    }*/
}
