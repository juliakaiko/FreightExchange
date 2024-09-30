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
        CarriageRequest actualOrder = orderRepository.getOne(1L);
        log.info("Test to find the Order with id = 1: "+actualOrder);
        Assert.assertNotNull(actualOrder);
        Assert.assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    public void save() {
        this.orderRepository.save(expectedOrder);
        CarriageRequest actualOrder =  orderRepository.getOne(expectedOrder.getId());
        log.info("Test to save the Order: "+actualOrder);
        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void delete() {
        this.orderRepository.save(expectedOrder);
        CarriageRequest actualOrder =  orderRepository.getOne(expectedOrder.getId());
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
}