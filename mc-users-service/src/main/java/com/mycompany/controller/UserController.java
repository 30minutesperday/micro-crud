package com.mycompany.controller;

import com.mycompany.model.dto.UpdateUserDto;
import com.mycompany.model.dto.UserDto;
import com.mycompany.repository.UserRepository;
import com.mycompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers(
            @PageableDefault(page=0, size=5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort="email", direction = Sort.Direction.DESC)
            }) Pageable pageable) {

        return userService.getUsers(pageable);
    }

    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @DeleteMapping("/{email}")
    public boolean deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    @PutMapping("/{email}")
    public String updateUser(@PathVariable String email,
                             @RequestBody UpdateUserDto userDto) {
        return userService.updateUser(email, userDto);
    }
}
