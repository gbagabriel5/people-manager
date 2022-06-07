package com.gba.people.manager.model.converter;

import com.gba.people.manager.dto.UserDto;
import com.gba.people.manager.model.User;
import static com.gba.people.manager.util.DateUtil.parseStringToLocalDateDDMMYYYY;
import static java.time.format.DateTimeFormatter.ofPattern;

public class UserConverter {

    private UserConverter() {}

    public static User convert(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .sex(dto.getSex())
                .birthdate(parseStringToLocalDateDDMMYYYY(dto.getBirthdate()))
                .naturalness(dto.getNaturalness())
                .nationality(dto.getNationality())
                .cpf(dto.getCpf())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static UserDto convert(User model) {
        String birthdate = model.getBirthdate().format(ofPattern("dd/MM/yyyy"));
        return UserDto.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .password(model.getPassword())
                .sex(model.getSex())
                .birthdate(birthdate)
                .naturalness(model.getNaturalness())
                .nationality(model.getNationality())
                .cpf(model.getCpf())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }
}