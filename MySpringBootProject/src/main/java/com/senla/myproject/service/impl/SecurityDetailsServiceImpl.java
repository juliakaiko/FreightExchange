package com.senla.myproject.service.impl;

import com.senla.myproject.model.CarrierManager;
import com.senla.myproject.model.FreightForwarder;
import com.senla.myproject.model.User;
import com.senla.myproject.repository.CarrierManagerRepository;
import com.senla.myproject.repository.FreightForwarderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SecurityDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private CarrierManagerRepository managerRepository;
    @Autowired
    private FreightForwarderRepository forwarderRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = null;
        Optional<CarrierManager> manager = managerRepository.findCarrierManagerByEmailIsLike(username);
        Optional<FreightForwarder> forwarder = forwarderRepository.findFreightForwarderByEmailIsLike(username);
        if (manager.isPresent()){
            userFromDb = manager.get();
        }
        if (forwarder.isPresent()){
            userFromDb = forwarder.get();
        }
        Optional <User> optionalUser = Optional.ofNullable(userFromDb);
        return optionalUser.map(user -> new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),
                Collections.singleton(optionalUser.get().getRole())
        )).orElseThrow(() -> new UsernameNotFoundException(username+ " not found"));
    }

}
