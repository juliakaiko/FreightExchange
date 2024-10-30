package com.senla.myproject.service;

import com.senla.myproject.dto.*;
import com.senla.myproject.mapper.*;
import com.senla.myproject.model.*;
import com.senla.myproject.repository.*;
import com.senla.myproject.service.impl.FreightExchangeServiceImpl;
import com.senla.myproject.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class) // Инициализирует моки
public class FreightExchangeServiceImplTest {

    @InjectMocks //создает имитирующую реализацию аннотированного типа и внедряет в нее зависимые имитирующие объекты
    private FreightExchangeServiceImpl service;
    @Mock // создает фиктивную реализацию для класса
    private CarrierRepository carrierRepository;
    @Mock
    private CarrierManagerRepository managerRepository;
    @Mock
    private FreightForwarderRepository forwarderRepository;
    @Mock
    private CarriageRequestRepository orderRepository;
    @Mock
    private TruckParkRepository parkRepository;

    //CarrierManager
    @Test
    public void findCarrierManagerById_thenReturnCarrierManagerDto() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        managerRepository.save(manager);
        //если вызывется метод getOne у репозитория, то возвращается manager
        // Стаббинг: определение поведения
        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        CarrierManagerDto result = service.findCarrierManagerById(manager.getId());
        //Верификация: был ли вызван метод getOne 1 раз
        verify(managerRepository, times(1)).findById(manager.getId());
        // Проверка: метод findCarrierManagerById сервиса возвращает сгенерированого manager
        log.info("FROM SERVICE_TEST: find the CarrierManager by id => manager: "+result);
        assertEquals(managerDto, result);
    }

    @Test
    public void findAllCarrierManagers_whenCorrect_ReturnCarrierManagerList() {
        CarrierManager manager1 = CarrierManagerGenerator.generateCarrierManager();
        CarrierManager manager2 = CarrierManagerGenerator.generateCarrierManager();
        manager2.setId(2l); manager2.setEmail("test2@test.by");
        CarrierManager manager3 = CarrierManagerGenerator.generateCarrierManager();
        manager3.setId(3l); manager3.setEmail("test3@test.by");
        List <CarrierManager> managerList = new ArrayList<>();
        managerList.add(manager1);
        managerList.add(manager2);
        managerList.add(manager3);
        // Стаббинг: определение поведения
        when(managerRepository.findAll()).thenReturn(managerList);

        List <CarrierManager> resultList = service.findAllCarrierManagers();
        //Верификация: был ли вызван метод findAll() 1 раз
        verify(managerRepository, times(1)).findAll();
        log.info("FROM SERVICE_TEST: find all the CarrierManagers => list: "+service.findAllCarrierManagers());
        //Проверка
        assertEquals(managerList, resultList);
    }

    @Test
    public void saveCarrierManager_whenCorrect_thenReturnCarrierManagerDto(){
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        CarrierManagerDto result = service.saveCarrierManager(managerDto);

        verify(managerRepository, times(1)).save(manager);

        log.info("FROM SERVICE_TEST: save the CarrierManager => manager: "+result);
        assertEquals(managerDto, result);
    }

    @Test
    public void deleteCarrierManagerById_whenCorrect_thenReturnCarrierManagerDto() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        CarrierManagerDto result = service.deleteCarrierManagerById(1l);
        //Верификация: был ли вызван метод deleteById 1 раз
        verify(managerRepository, times(1)).deleteById(manager.getId());

        log.info("FROM SERVICE_TEST: delete the CarrierManager by id => manager: "+result);
        assertEquals(managerDto, result);
    }

    //Carrier
    @Test
    public void findCarrierById_whenCorrect_thenReturnCarrierDto() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = CarrierMapper.INSTANSE.toDto(carrier);

        when(carrierRepository.findById(carrier.getId())).thenReturn(Optional.of(carrier));

        CarrierDto result = service.findCarrierById(carrier.getId());
        verify(carrierRepository, times(1)).findById(carrier.getId());

        log.info("FROM SERVICE_TEST: find the Carrier by id => carrier: "+result);
        assertEquals(carrierDto, result);
    }

    @Test
    public void saveCarrier_whenCorrect_thenReturnCarrierDto() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = CarrierMapper.INSTANSE.toDto(carrier);
        CarrierDto result = service.saveCarrier(carrierDto);

        verify(carrierRepository, times(1)).save(carrier);

        log.info("FROM SERVICE_TEST: save the Carrier => carrier: "+result);
        assertEquals(carrierDto, result);
    }

    @Test
    public void deleteCarrierById_whenCorrect_thenReturnCarrierDto() {
        Carrier carrier = CarrierGenerator.generateCarrier();
        CarrierDto carrierDto = CarrierMapper.INSTANSE.toDto(carrier);
        when(carrierRepository.findById(carrier.getId())).thenReturn(Optional.of(carrier));

        CarrierDto result = service.deleteCarrierById(carrier.getId());
        //Верификация: был ли вызван метод deleteById 1 раз
        verify(carrierRepository, times(1)).deleteById(carrier.getId());

        log.info("FROM SERVICE_TEST: delete the Carrier by id => carrier: "+result);
        assertEquals(carrierDto, result);
    }

    @Test
    public void findAllCarries_whenCorrect_thenReturnCarrierList() {
        Carrier carrier1 = CarrierGenerator.generateCarrier();
        Carrier carrier2 = CarrierGenerator.generateCarrier();
        carrier2.setId(2l); carrier2.setAddress("carrier1_address");
        Carrier carrier3 = CarrierGenerator.generateCarrier();
        carrier3.setId(3l); carrier3.setAddress("carrier3_address");
        List <Carrier> carrierList = new ArrayList<>();
        carrierList.add(carrier1);
        carrierList.add(carrier2);
        carrierList.add(carrier3);
        // Стаббинг: определение поведения
        when(carrierRepository.findAll()).thenReturn(carrierList);

        List <Carrier> resultList = service.findAllCarriers();
        //Верификация: был ли вызван метод findAll() 1 раз
        verify(carrierRepository, times(1)).findAll();
        log.info("FROM SERVICE_TEST: find all the Carriers => list: "+service.findAllCarriers());
        //Проверка
        assertEquals(carrierList, resultList);
    }

    //FreightForwarder
    @Test
    public void findFreightForwarderById_whenCorrect_thenReturnFreightForwarderDto() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);

        when(forwarderRepository.findById(forwarder.getId())).thenReturn(Optional.of(forwarder));

        FreightForwarderDto result = service.findFreightForwarderById(forwarder.getId());
        verify(forwarderRepository, times(1)).findById(forwarder.getId());

        log.info("FROM SERVICE_TEST: find the FreightForwarder by id => forwarder: "+result);
        assertEquals(forwarderDto, result);
    }

    @Test
    public void saveFreightForwarder_whenCorrect_thenReturnFreightForwarderDto() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);
        FreightForwarderDto result = service.saveFreightForwarder(forwarderDto);

        verify(forwarderRepository, times(1)).save(forwarder);

        log.info("FROM SERVICE_TEST: save the FreightForwarder => forwarder: "+result);
        assertEquals(forwarderDto, result);
    }

    @Test
    public void deleteFreightForwarderById_whenCorrect_thenReturnFreightForwarderDto() {
        FreightForwarder forwarder = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);
        when(forwarderRepository.findById(forwarder.getId())).thenReturn(Optional.of(forwarder));

        FreightForwarderDto result = service.deleteFreightForwarderById(forwarder.getId());
        //Верификация: был ли вызван метод deleteById 1 раз
        verify(forwarderRepository, times(1)).deleteById(forwarder.getId());

        log.info("FROM SERVICE_TEST: delete the FreightForwarder by id => forwarder: "+result);
        assertEquals(forwarderDto, result);
    }

    @Test
    public void findAllForwarders_whenCorrect_thenReturnFreightForwarderList() {
        FreightForwarder forwarder1 = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarder forwarder2 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder2.setId(2l); forwarder2.setEmail("test2@test.by");
        FreightForwarder forwarder3 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder3.setId(3l); forwarder3.setEmail("test3@test.by");
        List <FreightForwarder> forwarderList = new ArrayList<>();
        forwarderList.add(forwarder1);
        forwarderList.add(forwarder2);
        forwarderList.add(forwarder3);
        // Стаббинг: определение поведения
        when(forwarderRepository.findAll()).thenReturn(forwarderList);

        List <FreightForwarder> resultList = service.findAllForwarders();
        //Верификация: был ли вызван метод findAll() 1 раз
        verify(forwarderRepository, times(1)).findAll();
        log.info("FROM SERVICE_TEST: find all the FreightForwarders => list: "+service.findAllForwarders());
        //Проверка
        assertEquals(forwarderList, resultList);
    }

    //Order
    @Test
    public void findOrderById_whenCorrect_thenReturnOrderDto() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        CarriageRequestDto result = service.findOrderById(order.getId());
        verify(orderRepository, times(1)).findById(order.getId());

        log.info("FROM SERVICE_TEST: find the Order by id => order: "+result);
        assertEquals(orderDto, result);
    }

    @Test
    public void saveOrder_whenCorrect_thenReturnOrderDto() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);

        CarriageRequestDto result = service.saveOrder(orderDto);

        verify(orderRepository, times(1)).save(order);

        log.info("FROM SERVICE_TEST: save the Order => order: "+result);
        assertEquals(orderDto, result);
    }

    @Test
    public void deleteOrderById_whenCorrect_thenReturnOrderDto() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        CarriageRequestDto result = service.deleteOrderById(order.getId());
        //Верификация: был ли вызван метод deleteById 1 раз
        verify(orderRepository, times(1)).deleteById(order.getId());

        log.info("FROM SERVICE_TEST: delete the Order by id => order: "+result);
        assertEquals(orderDto, result);
    }

    @Test
    public void findAllOrders_whenCorrect_thenReturnOrderList() {
        CarriageRequest order1 = CarriageRequestGenerator.generateOrder();
        CarriageRequest order2 = CarriageRequestGenerator.generateOrder();
        order2.setId(2l);
        CarriageRequest order3 = CarriageRequestGenerator.generateOrder();
        order3.setId(3l);
        List <CarriageRequest> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        // Стаббинг: определение поведения
        when(orderRepository.findAll()).thenReturn(orderList);

        List <CarriageRequest> resultList = service.findAllOrders();
        //Верификация: был ли вызван метод findAll() 1 раз
        verify(orderRepository, times(1)).findAll();
        log.info("FROM SERVICE_TEST: find all the CarriageRequests => list: "+service.findAllOrders());
        //Проверка
        assertEquals(orderList, resultList);
    }

    //TruckPark
    @Test
    public void findTruckParkById_whenCorrect_thenReturnTruckParkDto() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = TruckParkMapper.INSTANSE.toDto(park);

        when(parkRepository.findById(park.getId())).thenReturn(Optional.of(park));

        TruckParkDto result = service.findTruckParkById(park.getId());
        verify(parkRepository, times(1)).findById(park.getId());

        log.info("FROM SERVICE_TEST: find the TruckPark by id => park: "+result);
        assertEquals(parkDto, result);
    }

    @Test
    public void saveTruckPark_whenCorrect_thenReturnTruckParkDto() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        Carrier carrier = CarrierGenerator.generateCarrier();
        park.setCarrier(carrier);
        TruckParkDto parkDto = TruckParkMapper.INSTANSE.toDto(park);

        TruckParkDto result = service.saveTruckPark(parkDto);

        verify(parkRepository, times(1)).save(park);

        log.info("FROM SERVICE_TEST: save the TruckPark => park: "+result);
        assertEquals(parkDto, result);
    }

    @Test
    public void deleteTruckParkById_whenCorrect_thenReturnTruckParkDto() {
        TruckPark park = TruckParkGenerator.generateTruckPark();
        TruckParkDto parkDto = TruckParkMapper.INSTANSE.toDto(park);
        when(parkRepository.findById(park.getId())).thenReturn(Optional.of(park));

        TruckParkDto result = service.deleteTruckParkById(park.getId());
        //Верификация: был ли вызван метод deleteById 1 раз
        verify(parkRepository, times(1)).deleteById(park.getId());

        log.info("FROM SERVICE_TEST: delete the TruckPark by id => park: "+result);
        assertEquals(parkDto, result);
    }

    @Test
    public void findAllTruckParks_whenCorrect_thenReturnTruckParkList() {
        TruckPark park1 = TruckParkGenerator.generateTruckPark();
        TruckPark park2 = TruckParkGenerator.generateTruckPark();
        park2.setId(2l);
        TruckPark park3 = TruckParkGenerator.generateTruckPark();
        park3.setId(3l);
        List <TruckPark> parkList = new ArrayList<>();
        parkList.add(park1);
        parkList.add(park2);
        parkList.add(park3);
        // Стаббинг: определение поведения
        when(parkRepository.findAll()).thenReturn(parkList);

        List <TruckPark> resultList = service.findAllTruckParks();
        //Верификация: был ли вызван метод findAll() 1 раз
        verify(parkRepository, times(1)).findAll();
        log.info("FROM SERVICE_TEST: find all the TruckParks => list: "+service.findAllTruckParks());
        //Проверка
        assertEquals(parkList, resultList);
    }
}