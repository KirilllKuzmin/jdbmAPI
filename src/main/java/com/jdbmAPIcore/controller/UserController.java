package com.jdbmAPIcore.controller;

import com.jdbmAPIcore.controller.dto.UserRequestDTO;
import com.jdbmAPIcore.controller.dto.UserResponseDTO;
import com.jdbmAPIcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/version")
    public String getVersion() {
        return "0.1.0";
    }

    @PostMapping("/createUsers")
    public Long createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createAccount(userRequestDTO.getName(), userRequestDTO.getLogin(), userRequestDTO.getPassword(), userRequestDTO.getPhone());
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return new UserResponseDTO(userService.getAccountById(id));
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAll() {
        return userService.getAll().stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    @DeleteMapping("/users/{id}")
    public UserResponseDTO delete(@PathVariable Long id) {
        return new UserResponseDTO(userService.deleteById(id));
    }

}
