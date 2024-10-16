package com.senla.myproject.repository;

import com.senla.myproject.model.CarriageRequest;
import com.senla.myproject.util.CarriageRequestGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CarriageRequestRepositoryImplTest {

    @Autowired
    private CarriageRequestRepository orderRepository;
    private static CarriageRequest expectedOrder;

    @BeforeClass
    public static void setUp(){
        expectedOrder = CarriageRequestGenerator.generateOrder();
    }

    @Test
    public void findById() {
        this.orderRepository.save(expectedOrder);
        CarriageRequest actualOrder = orderRepository.findById(1L).get();
        log.info("Test to find the Order with id = 1: "+actualOrder);
        Assert.assertNotNull(actualOrder);
        Assert.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void findByOrderNameIsLike() {
        this.orderRepository.save(expectedOrder);
        Optional<CarriageRequest> actualOrder = orderRepository.findById(1L);
        log.info("Test to find the Order with name "+actualOrder.get().getOrderName()+" :"+actualOrder.get());
        Assert.assertTrue(actualOrder.isPresent());
        actualOrder.ifPresent(expectedOrder -> Assert.assertEquals(expectedOrder.getOrderName(), actualOrder.get().getOrderName()));
    }

    @Test
    public void save() {
        this.orderRepository.save(expectedOrder);
        CarriageRequest actualOrder =  orderRepository.findById(expectedOrder.getId()).get();
        log.info("Test to save the Order: "+actualOrder);
        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void delete() {
        this.orderRepository.save(expectedOrder);
        CarriageRequest actualOrder =  orderRepository.findById(expectedOrder.getId()).get();
        orderRepository.delete(actualOrder);
        log.info("Test to delete the Order with id = 1: "+actualOrder);
        Optional<CarriageRequest> deletedOrder =orderRepository.findById(expectedOrder.getId());
        Assertions.assertFalse(deletedOrder.isPresent());
    }

    @Test
    public void findAll() {
        this.orderRepository.save(expectedOrder);
        log.info("Test to find all the Orders: "+ this.orderRepository.findAll());
        Assertions.assertFalse(this.orderRepository.findAll().isEmpty(),() -> "List of orders shouldn't be empty");
    }

    @Test
    public void findAllOrdersNative() {
        this.orderRepository.save(expectedOrder);
        var pageable  = PageRequest.of(0,5, Sort.by("id"));
        var slice = this.orderRepository.findAllOrdersNative(pageable);
        slice.forEach(order -> System.out.println(order));
        while (slice.hasNext()){
            slice = this.orderRepository.findAllOrdersNative(slice.nextPageable());
            slice.forEach(order -> System.out.println(order));
        }
    }
}