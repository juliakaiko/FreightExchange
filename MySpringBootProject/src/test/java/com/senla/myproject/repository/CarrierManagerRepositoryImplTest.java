package com.senla.myproject.repository;

import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.util.CarrierManagerGenerator;
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
public class CarrierManagerRepositoryImplTest {

    @Autowired
    private CarrierManagerRepository managerRepository;
    private static CarrierManager expectedManager;

    @BeforeClass
    public static void  setUp(){
        expectedManager = CarrierManagerGenerator.generateCarrierManager();
    }

    @Test
    public void findById() {
        this.managerRepository.save(expectedManager);
        CarrierManager actualManager = managerRepository.getOne(1L);
        log.info("Test to find the CarrierManager with id = 1: "+actualManager);
        Assert.assertNotNull(actualManager);
        Assert.assertEquals(expectedManager.getId(), actualManager.getId());
    }

    @Test
    public void save() {
        this.managerRepository.save(expectedManager);
        CarrierManager actualManager =  managerRepository.getOne(expectedManager.getId());
        log.info("Test to save the CarrierManager: "+actualManager);
        Assertions.assertEquals(expectedManager,actualManager);
    }

    @Test
    public void delete() {
        this.managerRepository.save(expectedManager);
        CarrierManager actualManager =  managerRepository.getOne(expectedManager.getId());
        managerRepository.delete(actualManager);
        log.info("Test to delete the CarrierManager with id = 1: "+actualManager);
        Optional<CarrierManager> deletedManager = managerRepository.findById(actualManager.getId());
        Assertions.assertFalse(deletedManager.isPresent());
    }

    @Test
    public void findAll() {
        this.managerRepository.save(expectedManager);
        log.info("Test to find all the CarrierManagers : "+ this.managerRepository.findAll());
        Assertions.assertFalse(this.managerRepository.findAll().isEmpty(),() -> "List of managers shouldn't be empty");
    }
}