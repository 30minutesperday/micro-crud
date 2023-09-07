package com.mycompany.service;

import com.mycompany.model.dto.UpdateUserDto;
import com.mycompany.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    String createUser(UserDto userDto);

    List<UserDto> getUsers(Pageable pageable);

    UserDto getUser(String email);

    boolean deleteUser(String email);

    String updateUser(String email, UpdateUserDto userDto);
}
