package com.gba.people.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenDto {
    private String token;
    private String type;

    public TokenDto(String token, String type) {
        this.token = token;
        this.type = type;
    }
}
