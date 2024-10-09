package com.senla.myproject.repository;

import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.util.FreightForwarderGenerator;
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
public class FreightForwarderRepositoryImplTest {

    @Autowired
    private FreightForwarderRepository forwarderRepository;
    private static FreightForwarder expectedForwarder;

    @BeforeClass
    public static void  init(){
        expectedForwarder = FreightForwarderGenerator.generateFreightForwarder();
    }

    @Test
    public void findById() {
        this.forwarderRepository.save(expectedForwarder);
        FreightForwarder actualForwarder = forwarderRepository.findById(1L).get();
        log.info("Test to find the FreightForwarder with id = 1: "+actualForwarder);
        Assert.assertNotNull(actualForwarder);
        Assert.assertEquals(expectedForwarder, actualForwarder);
    }

    @Test
    public void save() {
        this.forwarderRepository.save(expectedForwarder);
        FreightForwarder actualForwarder =  forwarderRepository.findById(expectedForwarder.getId()).get();
        log.info("Test to save the FreightForwarder: "+actualForwarder);
        Assertions.assertEquals(expectedForwarder, actualForwarder);
    }

    @Test
    public void delete() {
        this.forwarderRepository.save(expectedForwarder);
        FreightForwarder actualForwarder =  forwarderRepository.findById(expectedForwarder.getId()).get();
        forwarderRepository.delete(actualForwarder);
        log.info("Test to delete the  FreightForwarder with id = 1: "+actualForwarder);
        Optional< FreightForwarder> deletedForwarder = forwarderRepository.findById(actualForwarder.getId());
        Assertions.assertFalse(deletedForwarder.isPresent());
    }

    @Test
    public void findAll() {
        forwarderRepository.save(expectedForwarder);
        log.info("Test to find all the CarrierManagers : "+ this.forwarderRepository.findAll());
        Assertions.assertFalse(this.forwarderRepository.findAll().isEmpty(),() -> "List of forwarders shouldn't be empty");
    }
}