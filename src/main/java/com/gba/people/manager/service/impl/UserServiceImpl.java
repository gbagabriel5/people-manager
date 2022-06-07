package com.gba.people.manager.service.impl;

import com.gba.people.manager.config.exception.InvalidCpfException;
import com.gba.people.manager.config.exception.InvalidEmailException;
import com.gba.people.manager.config.exception.NullPointerException;
import com.gba.people.manager.dto.UserDto;
import com.gba.people.manager.dto.custom.UserCustomDto;
import com.gba.people.manager.model.User;
import com.gba.people.manager.model.converter.UserConverter;
import com.gba.people.manager.repository.UserRepository;
import com.gba.people.manager.service.SequenceGeneratorService;
import com.gba.people.manager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.gba.people.manager.model.converter.UserConverter.convert;
import static com.gba.people.manager.util.DateUtil.parseStringToLocalDateDDMMYYYY;
import static com.gba.people.manager.util.FormatPatternUtil.isInvalidPattern;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.time.LocalDateTime.now;

@Service
public record UserServiceImpl(
        UserRepository repository,
        SequenceGeneratorService sequenceGenerator
) implements UserService {
    private static final String STRING_REQUIRED_MESSAGE = "Preencha o campo obrigatorio";
    private static final String CPF_PATTERN = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public UserDto create(UserDto dto) {
        createFieldsValidation(dto);
        return convert(repository.save(prepareAndGetUserToSave(dto)));
    }

    @Override
    public UserDto update(UserCustomDto dto) {
        Optional<User> optUserReturned = repository.findById(dto.getId());
        if (optUserReturned.isPresent()) {
            return updateUser(dto, optUserReturned.get());
        } else {
            throw new EntityNotFoundException("Usuario nao encontrado!");
        }
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(UserConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDto initializeDatabase() {
        var user = User.builder()
                .id(sequenceGenerator.generateSequence(User.SEQUENCE_NAME))
                .name("Gabriel")
                .email("gba@gmail.com")
                .password(new BCryptPasswordEncoder().encode("123"))
                .sex("M")
                .birthdate(LocalDate.of(1998, 11, 6))
                .naturalness("amazonense")
                .nationality("brasileiro")
                .cpf("041.829.142-01")
                .createdAt(now())
                .updatedAt(now())
                .build();
        if (repository.findAll().isEmpty()) {
            return convert(repository.save(user));
        }
        return null;
    }

    private void createFieldsValidation(UserDto dto) {
        if (isNullOrEmpty(dto.getName())) {
            throw new NullPointerException(STRING_REQUIRED_MESSAGE + "NOME!");
        }
        if (isNullOrEmpty(dto.getPassword())) {
            throw new NullPointerException(STRING_REQUIRED_MESSAGE + "SENHA!");
        }
        if (isNullOrEmpty(dto.getEmail())) {
            throw new NullPointerException(STRING_REQUIRED_MESSAGE + "EMAIL!");
        } else {
            if (isInvalidPattern(dto.getEmail(), EMAIL_PATTERN)) {
                throw new InvalidEmailException("Formato de Email invalido!");
            }
        }
        if (isNullOrEmpty(dto.getCpf())) {
            throw new NullPointerException(STRING_REQUIRED_MESSAGE + "CPF!");
        } else {
            if (isInvalidPattern(dto.getCpf(), CPF_PATTERN)) {
                throw new InvalidCpfException("Formato de CPF invalido!");
            }
        }
    }

    private User prepareAndGetUserToSave(UserDto dto) {
        var user = convert(dto);
        user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        user.setPassword(encryptPassword(dto.getPassword()));
        user.setCreatedAt(now());
        user.setUpdatedAt(now());
        return user;
    }

    private UserDto updateUser(UserCustomDto dto, User userReturned) {
        dto.setPassword(encryptPassword(dto.getPassword()));
        LocalDate birthDate = parseStringToLocalDateDDMMYYYY(dto.getBirthdate());
        String password = getNewOrKeepOldString(dto.getPassword(), userReturned.getPassword());
        var user = userReturned.toBuilder()
                .id(dto.getId())
                .name(getNewOrKeepOldString(dto.getName(), userReturned.getName()))
                .password(password.equals(dto.getPassword()) ? encryptPassword(password) : password)
                .sex(getNewOrKeepOldString(dto.getSex(), userReturned.getSex()))
                .birthdate(!isNullOrEmpty(dto.getBirthdate()) ? birthDate : userReturned.getBirthdate())
                .naturalness(getNewOrKeepOldString(dto.getNaturalness(), userReturned.getNaturalness()))
                .nationality(getNewOrKeepOldString(dto.getNationality(), userReturned.getNationality()))
                .build();
        return convert(repository.save(user));
    }

    private String getNewOrKeepOldString(String newString, String oldString) {
        return !isNullOrEmpty(newString) ? newString : oldString;
    }

    private String encryptPassword(String password) {
        return !isNullOrEmpty(password) ? new BCryptPasswordEncoder().encode(password) : null;
    }
}