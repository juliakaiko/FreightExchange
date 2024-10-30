package com.senla.myproject.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority { //GrantedAuthority - это полномочия, которые предоставляются пользователю

    MANAGER ("MANAGER"),
    FORWARDER ("FORWARDER"),
    ADMIN("ADMIN");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
