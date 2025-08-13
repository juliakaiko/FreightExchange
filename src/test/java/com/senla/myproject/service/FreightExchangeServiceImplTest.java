package com.senla.myproject.service;

import com.senla.myproject.dto.*;
import com.senla.myproject.exceptions.NotFoundException;
import com.senla.myproject.exceptions.OrderNotValidException;
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
import org.springframework.data.domain.*;

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
    public void saveCarrierManager_whenCorrect_thenReturnCarrierManagerDto(){
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        CarrierManagerDto result = service.saveCarrierManager(managerDto);

        verify(managerRepository, times(1)).save(manager);

        log.info("FROM SERVICE_TEST: save the CarrierManager => manager: "+result);
        assertEquals(managerDto, result);
    }

    @Test
    public void findCarrierManagerById_thenReturnCarrierManagerDto() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        managerRepository.save(manager);
        //если вызывется метод findById у репозитория, то возвращается manager
        // Стаббинг: определение поведения
        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        CarrierManagerDto result = service.findCarrierManagerById(manager.getId());
        //Верификация: был ли вызван метод findById 1 раз
        verify(managerRepository, times(1)).findById(manager.getId());
        // Проверка: метод findCarrierManagerById сервиса возвращает сгенерированого manager
        log.info("FROM SERVICE_TEST: find the CarrierManager by id => manager: "+result);
        assertEquals(managerDto, result);
    }

    @Test
    public void findCarrierManagerByEmail_whenCorrect_thenReturnCarrierManagerDto() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        CarrierManagerDto managerDto = CarrierManagerMapper.INSTANSE.toDto(manager);
        managerRepository.save(manager);
        //если вызывется метод findCarrierManagerByEmailIsLike у репозитория, то возвращается manager
        // Стаббинг: определение поведения
        when(managerRepository.findCarrierManagerByEmailIsLike(manager.getEmail())).thenReturn(Optional.of(manager));

        CarrierManagerDto result = service.findCarrierManagerByEmailIsLike(manager.getEmail());
        //Верификация: был ли вызван метод findCarrierManagerByEmailIsLike 1 раз
        verify(managerRepository, times(1)).findCarrierManagerByEmailIsLike(manager.getEmail());
        // Проверка: метод findCarrierManagerById сервиса возвращает сгенерированого manager
        log.info("FROM SERVICE_TEST: find the CarrierManager by email => manager: "+result);
        assertEquals(managerDto, result);
    }

    @Test
    public void findCarrierManagerByEmail_whenInCorrect_thenReturnNotFoundException() {
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        manager.setEmail("incorrect@mail.ru");
        managerRepository.save(manager);
        try {
            when(managerRepository.findCarrierManagerByEmailIsLike(manager.getEmail())).thenReturn(Optional.of(manager));
            service.findCarrierManagerByEmailIsLike(manager.getEmail());
            verify(managerRepository, times(1)).findCarrierManagerByEmailIsLike(manager.getEmail());
        } catch (NotFoundException e){
            System.out.println("NotFoundException: "+e.getMessage());
            assertEquals("CarrierManager wasn't found by email " + manager.getEmail(), e.getMessage());
        }
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
    public void findAllManagersNativeWithPagination_whenCorrect_ReturnCarrierManagerList() {
        log.info("FROM SERVISE: findAllManagersNativeWithPagination()");

        CarrierManager manager1 = CarrierManagerGenerator.generateCarrierManager();
        CarrierManager manager2 = CarrierManagerGenerator.generateCarrierManager();
        manager2.setId(2l); manager2.setEmail("test2@test.by");
        CarrierManager manager3 = CarrierManagerGenerator.generateCarrierManager();
        manager3.setId(3l); manager3.setEmail("test3@test.by");
        List<CarrierManager> managerList = new ArrayList<>();
        managerList.add(manager1);
        managerList.add(manager2);
        managerList.add(manager3);
        //managerRepository.save(manager1); managerRepository.save(manager2); managerRepository.save(manager3);
        var pageable  = PageRequest.of(0,1, Sort.by("id"));
        Page<CarrierManager> managerPagebleList = new PageImpl<CarrierManager> (managerList, pageable,3);
        Page<CarrierManagerDto> managerPagebleListDto = managerPagebleList.map(CarrierManagerMapper.INSTANSE::toDto);

        when(this.managerRepository.findAllManagersNative(pageable)).thenReturn(managerPagebleList);

        Page<CarrierManagerDto> resultListDto = service.findAllManagersNativeWithPagination(0,1);
        //Page<CarrierManager> resultList = resultListDto.map(CarrierManagerMapper.INSTANSE::toEntity);

        verify(managerRepository, times(1)).findAllManagersNative(pageable);

        assertEquals(managerPagebleListDto, resultListDto);
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
    public void findAllCarriers_whenCorrect_thenReturnCarrierList() {
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

    @Test
    public  void findAllCarriersNativeWithPagination_whenCorrect_ReturnCarrierList() {
        log.info("FROM SERVISE: findAllCarriersNativeWithPagination()");

        Carrier carrier1 = CarrierGenerator.generateCarrier();
        Carrier carrier2 = CarrierGenerator.generateCarrier();
        carrier2.setId(2l); carrier2.setAddress("carrier1_address");
        Carrier carrier3 = CarrierGenerator.generateCarrier();
        carrier3.setId(3l); carrier3.setAddress("carrier3_address");
        List <Carrier> carrierList = new ArrayList<>();
        carrierList.add(carrier1);
        carrierList.add(carrier2);
        carrierList.add(carrier3);
        //managerRepository.save(manager1); managerRepository.save(manager2); managerRepository.save(manager3);
        var pageable  = PageRequest.of(0,1, Sort.by("id"));
        Page<Carrier> carrierPagebleList = new PageImpl<Carrier> (carrierList, pageable,3);
        Page<CarrierDto> carrierPagebleListDto = carrierPagebleList.map(CarrierMapper.INSTANSE::toDto);

        when(this.carrierRepository.findAllCarriersNative(pageable)).thenReturn(carrierPagebleList);

        Page<CarrierDto> resultListDto = service.findAllCarriersNativeWithPagination(0,1);
        Page<Carrier> resultList = resultListDto.map(CarrierMapper.INSTANSE::toEntity);
        //resultList.forEach(carrier -> System.out.println(carrier));

        verify(carrierRepository, times(1)).findAllCarriersNative(pageable);

        assertEquals(carrierPagebleListDto, resultListDto);
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
    public void findFreightForwarderByEmail_whenCorrect_thenReturnFreightForwarderDto() {
        FreightForwarder forwarder =FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarderDto forwarderDto = FreightForwarderMapper.INSTANSE.toDto(forwarder);
        forwarderRepository.save(forwarder);
        //если вызывется метод findCarrierManagerByEmailIsLike у репозитория, то возвращается manager
        // Стаббинг: определение поведения
        when(forwarderRepository.findFreightForwarderByEmailIsLike(forwarder.getEmail())).thenReturn(Optional.of(forwarder));

        FreightForwarderDto result = service.findFreightForwarderByEmailIsLike(forwarder.getEmail());
        //Верификация: был ли вызван метод findCarrierManagerByEmailIsLike 1 раз
        verify(forwarderRepository, times(1)).findFreightForwarderByEmailIsLike(forwarder.getEmail());
        // Проверка: метод findCarrierManagerById сервиса возвращает сгенерированого manager
        log.info("FROM SERVICE_TEST: find the FreightForwarder by email => forwarder: "+result);
        assertEquals(forwarderDto, result);
    }

    @Test
    public void findFreightForwarderByEmail_whenInCorrect_thenReturnNotFoundException() {
        FreightForwarder forwarder =FreightForwarderGenerator.generateFreightForwarder();
        forwarder.setEmail("incorrect@mail.ru");
        forwarderRepository.save(forwarder);
        try {
            when(forwarderRepository.findFreightForwarderByEmailIsLike(forwarder.getEmail())).thenReturn(Optional.of(forwarder));
            service.findFreightForwarderByEmailIsLike(forwarder.getEmail());
            verify(forwarderRepository, times(1)).findFreightForwarderByEmailIsLike(forwarder.getEmail());
        } catch (NotFoundException e){
            System.out.println("NotFoundException: "+e.getMessage());
            assertEquals("FreightForwarder wasn't found by email " + forwarder.getEmail(), e.getMessage());
        }
        log.info("FROM SERVICE_TEST: Fail to find the FreightForwarder by email: "+forwarder.getEmail());
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

    @Test
    public void findAllForwardersNativeWithPagination_whenCorrect_ReturnForwarderList() {
        log.info("FROM SERVISE: findAllForwardersNativeWithPagination()");

        FreightForwarder forwarder1 = FreightForwarderGenerator.generateFreightForwarder();
        FreightForwarder forwarder2 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder2.setId(2l); forwarder2.setEmail("test2@test.by");
        FreightForwarder forwarder3 = FreightForwarderGenerator.generateFreightForwarder();
        forwarder3.setId(3l); forwarder3.setEmail("test3@test.by");
        List <FreightForwarder> forwarderList = new ArrayList<>();
        forwarderList.add(forwarder1);
        forwarderList.add(forwarder2);
        forwarderList.add(forwarder3);

        var pageable  = PageRequest.of(0,1, Sort.by("id"));
        Page<FreightForwarder> forwarderPagebleList = new PageImpl<FreightForwarder> (forwarderList, pageable,3);
        Page<FreightForwarderDto> forwarderPagebleListDto = forwarderPagebleList.map(FreightForwarderMapper.INSTANSE::toDto);

        when(this.forwarderRepository.findAllFreightForwardersNative(pageable)).thenReturn(forwarderPagebleList);

        Page<FreightForwarderDto> resultListDto = service.findAllForwardersNativeWithPagination(0,1);
        //Page<FreightForwarder> resultList = resultListDto.map(FreightForwarderMapper.INSTANSE::toEntity);
        //resultList.forEach(forwarder -> System.out.println(forwarder));

        verify(forwarderRepository, times(1)).findAllFreightForwardersNative(pageable);

        assertEquals(forwarderPagebleListDto, resultListDto);
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
    public void findOrderByName_whenCorrect_thenReturnOrderDto(){
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);
        orderRepository.save(order);
        //если вызывется метод findCarrierManagerByEmailIsLike у репозитория, то возвращается manager
        // Стаббинг: определение поведения
        when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));

        CarriageRequestDto result = service.findOrderByName(order.getOrderName());
        //Верификация: был ли вызван метод findCarrierManagerByEmailIsLike 1 раз
        verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());
        // Проверка: метод findCarrierManagerById сервиса возвращает сгенерированого manager
        log.info("FROM SERVICE_TEST: find the Order by name => order: "+result);
        assertEquals(orderDto, result);
    }

    @Test
    public void findOrderByName_whenInCorrect_thenReturnOrderDto(){
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        order.setOrderName("incorrectName");
        orderRepository.save(order);
        try {
            when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));
            service.findOrderByName(order.getOrderName());
            verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());
        } catch (NotFoundException e){
            System.out.println("NotFoundException: "+e.getMessage());
            assertEquals("Order wasn't found by name " + order.getOrderName(), e.getMessage());
        }
        log.info("FROM SERVICE_TEST: Fail to find the Order by name: "+order.getOrderName());
    }

    @Test
    public void takeValidOrder_whenCorrect_thenReturnOrderDto() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();

        when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));
        CarriageRequestDto result = service.takeValidOrder(manager, order.getOrderName());
        verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());

        if (order.getValid() == true) {
            order.setValid(false);
            order.setManager(manager);
            manager.getOrders().add(order);
        }
        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);

        log.info("FROM SERVICE_TEST: takeValidOrder() => order: "+order);
        assertFalse(order.getValid());
        assertEquals(orderDto, result);
    }

    @Test
    public void takeValidOrder_whenInCorrect_thenThrowOrderNotValidException() {
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        order.setValid(false);
        log.info("FROM SERVICE_TEST: takeValidOrder_whenInCorrect_thenThrowOrderNotValidException() => order: "+order);
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        try {
            when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));
            CarriageRequestDto result = service.takeValidOrder(manager, order.getOrderName());
            verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());
        } catch (OrderNotValidException e){
            System.out.println("OrderNotValidException: "+e.getMessage());
            assertEquals("This order is not valid", e.getMessage());
        }
    }

    @Test
    public void cancelOrder_whenCorrect_thenReturnOrderDto(){
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        manager.getOrders().add(order);

        when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));
        CarriageRequestDto result = service.cancelOrder(manager, order.getOrderName());
        verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());

        Set<CarriageRequest> managerOrders = manager.getOrders();
        boolean found = false;
        for (CarriageRequest managerOrder: managerOrders){
            if(managerOrder.equals(order)){
                order.setValid(true);
                order.setManager(null);
                manager.getOrders().remove(order);
                found = true;
            }
        }

        CarriageRequestDto orderDto = CarriageRequestMapper.INSTANSE.toDto(order);

        log.info("FROM SERVICE_TEST: cancelOrderwhenCorrect_thenReturnOrderDto() => order: "+order);
        assertTrue(order.getValid());
        assertEquals(orderDto, result);
    }

    @Test
    public void cancelOrder_whenInCorrect_thenReturnNotFoundException(){
        CarriageRequest order = CarriageRequestGenerator.generateOrder();
        CarrierManager manager = CarrierManagerGenerator.generateCarrierManager();
        try {
            when(orderRepository.findByOrderNameIsLike(order.getOrderName())).thenReturn(Optional.of(order));
            CarriageRequestDto result = service.cancelOrder(manager, order.getOrderName());
            verify(orderRepository, times(1)).findByOrderNameIsLike(order.getOrderName());
        } catch (NotFoundException e){
            System.out.println("NotFoundException: "+e.getMessage());
            assertEquals("This manager doesn't have such order", e.getMessage());
        }
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

    @Test
    public void findAllOrdersNativeWithPagination_whenCorrect_ReturnOrderList() {
        log.info("FROM SERVISE: findAllOrdersNativeWithPagination()");

        CarriageRequest order1 = CarriageRequestGenerator.generateOrder();
        CarriageRequest order2 = CarriageRequestGenerator.generateOrder();
        order2.setId(2l);
        CarriageRequest order3 = CarriageRequestGenerator.generateOrder();
        order3.setId(3l);
        List <CarriageRequest> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);

        var pageable  = PageRequest.of(0,1, Sort.by("id"));
        Page<CarriageRequest> orderPagebleList = new PageImpl<CarriageRequest> (orderList, pageable,3);
        Page<CarriageRequestDto> orderPagebleListDto = orderPagebleList.map(CarriageRequestMapper.INSTANSE::toDto);

        when(this.orderRepository.findAllOrdersNative(pageable)).thenReturn(orderPagebleList);

        Page<CarriageRequestDto> resultListDto = service.findAllOrdersNativeWithPagination(0,1);
        Page<CarriageRequest> resultList = resultListDto.map(CarriageRequestMapper.INSTANSE::toEntity);
        resultList.forEach(order -> System.out.println(order));

        verify(orderRepository, times(1)).findAllOrdersNative(pageable);

        assertEquals(orderPagebleListDto, resultListDto);
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

    @Test
    public void findAllTruckParksNativeWithPagination_whenCorrect_ReturnTruckParkList() {
        log.info("FROM SERVISE: findAllTruckParksNativeWithPagination()");
        TruckPark park1 = TruckParkGenerator.generateTruckPark();
        TruckPark park2 = TruckParkGenerator.generateTruckPark();
        park2.setId(2l);
        TruckPark park3 = TruckParkGenerator.generateTruckPark();
        park3.setId(3l);
        List <TruckPark> parkList = new ArrayList<>();
        parkList.add(park1);
        parkList.add(park2);
        parkList.add(park3);

        var pageable  = PageRequest.of(0,1);
        Page<TruckPark> parkPagebleList = new PageImpl<TruckPark> (parkList, pageable,3);
        Page<TruckParkDto> parkPagebleListDto = parkPagebleList.map(TruckParkMapper.INSTANSE::toDto);

        when(this.parkRepository.findAllTruckParksNative(pageable)).thenReturn(parkPagebleList);

        Page<TruckParkDto> resultListDto = service.findAllTruckParksNativeWithPagination(0,1);
        Page<TruckPark> resultList = resultListDto.map(TruckParkMapper.INSTANSE::toEntity);
        resultList.forEach(park-> System.out.println(park));

        verify(parkRepository, times(1)).findAllTruckParksNative(pageable);

        assertEquals(parkPagebleListDto, resultListDto);
    }
}