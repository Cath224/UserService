package com.ateupeonding.userservice.controller;

import com.ateupeonding.userservice.model.dto.UserDto;
import com.ateupeonding.userservice.model.entity.User;
import com.ateupeonding.userservice.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user-service/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setLogin(userDto.getLogin());
        user = userService.create(user);
        userDto.setId(user.getId());
        userDto.setPassword(null);
        userDto.setCreatedTimestamp(user.getCreatedTimestamp());
        return userDto;
    }

    @PutMapping("{id}")
    public UserDto update(@PathVariable UUID id, @RequestBody UserDto userDto) {
        User user = new User();
        user.setId(id);
        user.setPassword(userDto.getPassword());
        user.setLogin(userDto.getLogin());
        user = userService.update(user);
        userDto.setLogin(user.getLogin());
        userDto.setPassword(null);
        return userDto;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @GetMapping("{id}")
    public UserDto getById(@PathVariable UUID id) {
        User user = userService.getById(id);
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        userDto.setCreatedTimestamp(user.getCreatedTimestamp());
        return userDto;
    }

    @GetMapping
    public List<UserDto> get(@RequestParam String login) {
        User user = userService.getByLogin(login);
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        userDto.setCreatedTimestamp(user.getCreatedTimestamp());
        return Collections.singletonList(userDto);
    }

}
