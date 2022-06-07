package com.gba.people.manager.dto.custom;

import lombok.*;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserCustomDto {
    private Integer id;
    private String name;
    private String password;
    private String sex;
    private String birthdate;
    private String naturalness;
    private String nationality;
}