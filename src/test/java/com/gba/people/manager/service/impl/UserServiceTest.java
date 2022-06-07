package com.gba.people.manager.service.impl;

import com.gba.people.manager.config.exception.InvalidCpfException;
import com.gba.people.manager.config.exception.InvalidEmailException;
import com.gba.people.manager.config.exception.NullPointerException;
import com.gba.people.manager.dto.UserDto;
import com.gba.people.manager.dto.custom.UserCustomDto;
import com.gba.people.manager.repository.UserRepository;
import com.gba.people.manager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    UserService service;

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(repository);
        repository.deleteAll();
    }

    @Test
    void shouldCreateUser() {
        UserDto userDto = getUserDto();

        var result = service.create(userDto);

        assertThat(result.getId()).isEqualTo(userDto.getId());
        assertThat(result.getName()).isEqualTo(userDto.getName());
        assertThat(result.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(result.getPassword()).isEqualTo(userDto.getPassword());
        assertThat(result.getSex()).isEqualTo(userDto.getSex());
        assertThat(result.getBirthdate()).isEqualTo(userDto.getBirthdate());
        assertThat(result.getNaturalness()).isEqualTo(userDto.getNaturalness());
        assertThat(result.getNationality()).isEqualTo(userDto.getNationality());
        assertThat(result.getCpf()).isEqualTo(userDto.getCpf());
    }

    @Test
    void shouldNotCreateUserWhithoutName() {
        var userDto = getUserDto().toBuilder().name(null).build();
        assertThrows(NullPointerException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldNotCreateUserWhithoutPassword() {
        var userDto = getUserDto().toBuilder().password(null).build();
        assertThrows(NullPointerException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldNotCreateUserWhithoutEmail() {
        var userDto = getUserDto().toBuilder().email(null).build();
        assertThrows(NullPointerException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldNotCreateUserWhithoutCpf() {
        var userDto = getUserDto().toBuilder().cpf(null).build();
        assertThrows(NullPointerException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldNotCreateUserWhithWrongEmailPattern() {
        var userDto = getUserDto().toBuilder().email("test").build();
        assertThrows(InvalidEmailException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldNotCreateUserWhithWrongCpfPattern() {
        var userDto = getUserDto().toBuilder().cpf("1111").build();
        assertThrows(InvalidCpfException.class, ()-> service.create(userDto));
    }

    @Test
    void shouldUpdateUser() {
        UserCustomDto customDto = getCustomDto();
        UserDto userDto = getUserDto();
        service.create(userDto);

        var result = service.update(customDto);

        assertThat(result.getId()).isEqualTo(userDto.getId());
        assertThat(result.getName()).isEqualTo(customDto.getName());
        assertThat(result.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(result.getPassword()).isNotEqualTo(customDto.getPassword());
        assertThat(result.getSex()).isEqualTo(customDto.getSex());
        assertThat(result.getBirthdate()).isEqualTo(customDto.getBirthdate());
        assertThat(result.getNaturalness()).isEqualTo(customDto.getNaturalness());
        assertThat(result.getNationality()).isEqualTo(customDto.getNationality());
        assertThat(result.getCpf()).isEqualTo(userDto.getCpf());
    }

    @Test
    void shouldNotUpdateUserThatNotExists() {
        var customDto = getCustomDto().toBuilder()
                .id(2)
                .build();
        UserDto userDto = getUserDto();
        service.create(userDto);

        assertThrows(EntityNotFoundException.class, ()-> service.update(customDto));
    }

    @Test
    void shouldGetAllUsers() {
        UserDto user1 = getUserDto();
        UserDto user2 = getUserDto().toBuilder().id(2).build();
        List.of(user1, user2).forEach(service::create);

        List<UserDto> userList = service.getAll();

        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList.get(0).getId()).isEqualTo(user1.getId());
        assertThat(userList.get(1).getId()).isEqualTo(user2.getId());
    }

    @Test
    void shouldDeleteUser() {
        UserDto userDto = getUserDto();
        service.create(userDto);

        boolean result = service.delete(1);

        List<UserDto> userList = service.getAll();

        assertThat(result).isTrue();
        assertThat(userList).isEmpty();
    }

    @Test
    void shouldNotDeleteUserThatNotExist() {
        boolean result = service.delete(3);

        assertThat(result).isFalse();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .name("test")
                .email("gba@gmail.com")
                .password("test")
                .sex("M")
                .birthdate("06/11/1998")
                .naturalness("test")
                .nationality("test")
                .cpf("041.829.142-01")
                .build();
    }

    private UserCustomDto getCustomDto() {
        return UserCustomDto.builder()
                .id(1)
                .name("test1")
                .password("test1")
                .sex("M")
                .birthdate("07/11/1998")
                .naturalness("test1")
                .nationality("test1")
                .build();
    }
}