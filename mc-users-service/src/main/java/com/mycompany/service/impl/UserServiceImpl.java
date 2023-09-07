package com.mycompany.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.exception.UserException;
import com.mycompany.model.dto.UpdateUserDto;
import com.mycompany.model.dto.UserDto;
import com.mycompany.model.entity.User;
import com.mycompany.repository.UserRepository;
import com.mycompany.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ObjectMapper objectMapper;
    public UserServiceImpl(UserRepository repository,
                           ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createUser(UserDto userDto) {
        // business logic
        try {
            String json = objectMapper.writeValueAsString(userDto);
            User user = objectMapper.readValue(json, User.class);
            repository.save(user);
            return user.getId().toString();
        } catch (JsonProcessingException e) {
            throw new UserException("500",e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<UserDto> getUsers(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(u -> {
                    try {
                        String json = objectMapper.writeValueAsString(u);
                        return objectMapper.readValue(json, UserDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(String email) {
         Optional<User> user = repository.findByEmail(email);

         if (user.isPresent()) {
             UserDto userDto = new UserDto();
             BeanUtils.copyProperties(user.get(), userDto);
             return userDto;
         }
         throw new UserException("404","No record found",HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public boolean deleteUser(String email) {
        long deleted = repository.deleteByEmail(email);
        if (deleted == 1) {
            return true;
        }
        throw new RuntimeException("No record found");
    }

    @Override
    @Transactional
    public String updateUser(String email, UpdateUserDto userDto) {
        return repository.findByEmail(email).map(u -> {
            u.setName(userDto.getName());
            return u.getId().toString();
        }).orElseThrow(() -> new RuntimeException("No record found"));
    }


}
