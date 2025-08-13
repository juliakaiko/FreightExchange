package com.senla.myproject.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    MANAGER ("MANAGER"),
    FORWARDER ("FORWARDER");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
