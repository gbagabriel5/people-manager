package com.gba.people.manager.dto;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record ProfileDto (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id,
        String name
) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return name;
    }
}
