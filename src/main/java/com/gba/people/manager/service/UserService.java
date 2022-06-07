package com.gba.people.manager.service;

import com.gba.people.manager.dto.UserDto;
import com.gba.people.manager.dto.custom.UserCustomDto;
import java.util.List;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto update(UserCustomDto dto);
    List<UserDto> getAll();
    boolean delete(Integer id);
    UserDto initializeDatabase();
}
