package com.gba.people.manager.controller;

import com.gba.people.manager.dto.UserDto;
import com.gba.people.manager.dto.custom.UserCustomDto;
import com.gba.people.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "User Controller")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Create User")
    public ResponseEntity<UserDto> create(@ApiParam(value = "User") @RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Update User")
    public UserDto update(@ApiParam(value = "User") @RequestBody UserCustomDto dto) {
        return userService.update(dto);
    }

    @GetMapping
    @ApiParam(value = "List All Users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete User by ID")
    public boolean delete(@PathVariable Integer id) {
        return userService.delete(id);
    }
}