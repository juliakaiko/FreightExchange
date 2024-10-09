package com.senla.myproject.repository;

import com.senla.myproject.model.TruckPark;
import com.senla.myproject.util.TruckParkGenerator;
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
public class TruckParkRepositoryImplTest {

    @Autowired
    private TruckParkRepository parkRepository;
    private static TruckPark expectedPark;

    @BeforeClass
    public static void  init(){
        expectedPark = TruckParkGenerator.generateTruckPark();
    }

    @Test
    public void findById() {
        this.parkRepository.save(expectedPark);
        TruckPark actualTruckPark = parkRepository.findById(1L).get();
        log.info("Test to find the TruckPark with id = 1: "+actualTruckPark );
        Assert.assertNotNull(actualTruckPark);
        Assert.assertEquals(expectedPark, actualTruckPark);
    }

    @Test
    public void save() {
        this.parkRepository.save(expectedPark);
        TruckPark actualTruckPark = parkRepository.findById(expectedPark.getId()).get();
        log.info("Test to save the TruckPark: "+actualTruckPark);
        Assertions.assertEquals(expectedPark, actualTruckPark);
    }

    @Test
    public void delete() {
        this.parkRepository.save(expectedPark);
        TruckPark actualTruckPark = parkRepository.findById(expectedPark.getId()).get();
        this.parkRepository.delete(actualTruckPark);
        log.info("Test to delete the TruckPark with id = 1: "+actualTruckPark);
        Optional<TruckPark> deletedPark = parkRepository.findById(actualTruckPark.getId());
        Assertions.assertFalse(deletedPark.isPresent());
    }

    @Test
    public void findAll() {
        this.parkRepository.save(expectedPark);
        log.info("Test to find all the TruckParks : "+ this.parkRepository.findAll());
        Assertions.assertFalse(this.parkRepository.findAll().isEmpty(), () -> "List of truck's parks shouldn't be empty");
    }
}