package com.gba.people.manager.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "People")
public class User implements UserDetails {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String sex;
    private LocalDate birthdate;
    private String naturalness;
    private String nationality;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}