package com.senla.myproject.repository;

import com.senla.myproject.model.Carrier;
import com.senla.myproject.util.CarrierGenerator;
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
public class CarrierRepositoryImplTest {

    @Autowired
    private CarrierRepository carrierRepository;
    private static Carrier expectedCarrier;

    @BeforeClass
    public static void  init(){
        expectedCarrier = CarrierGenerator.generateCarrier();
    }

    @Test
    public void findById() {
        this.carrierRepository.save(expectedCarrier);
        Carrier actualCarrier = carrierRepository.findById(1L).get();
        log.info("Test to find the Carrier with id = 1: "+actualCarrier);
        Assert.assertNotNull(actualCarrier);
        Assert.assertEquals(expectedCarrier, actualCarrier);
    }

    @Test
    public void save() {
        this.carrierRepository.save(expectedCarrier);
        Carrier actualCarrier =  carrierRepository.findById(expectedCarrier.getId()).get();
        log.info("Test to save the Carrier: "+actualCarrier);
        Assertions.assertEquals(expectedCarrier, actualCarrier);
    }

    @Test
    public void delete() {
        this.carrierRepository.save(expectedCarrier);
        Carrier actualCarrier =  carrierRepository.findById(expectedCarrier.getId()).get();
        carrierRepository.delete(actualCarrier);
        log.info("Test to delete the Carrier with id = 1: "+expectedCarrier);
        Optional<Carrier> deletedCarrier = carrierRepository.findById(actualCarrier.getId());
        Assertions.assertFalse(deletedCarrier.isPresent());
    }

    @Test
    public void findAll() {
        this.carrierRepository.save(expectedCarrier);
        log.info("Test to find all the Carriers: "+ this.carrierRepository.findAll());
        Assertions.assertFalse(this.carrierRepository.findAll().isEmpty(),() -> "List of carriers shouldn't be empty");
    }

    @Test
    public void findAllCarriersNative() {
        this.carrierRepository.save(expectedCarrier);
        log.info("Test to find findAllCarriersNative()");
        var pageable  = PageRequest.of(0,1, Sort.by("id"));
        var slice = this.carrierRepository.findAllCarriersNative(pageable);
        slice.forEach(carrier -> System.out.println(carrier));
        while (slice.hasNext()){
            slice = this.carrierRepository.findAllCarriersNative(slice.nextPageable());
            slice.forEach(carrier -> System.out.println(carrier));
        }
    }
}