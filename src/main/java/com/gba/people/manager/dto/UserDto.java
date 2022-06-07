package com.gba.people.manager.dto;

import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String sex;
    private String birthdate;
    private String naturalness;
    private String nationality;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}