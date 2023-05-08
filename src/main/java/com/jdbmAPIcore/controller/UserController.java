package com.jdbmAPIcore.controller;

import com.jdbmAPIcore.controller.dto.UserRequestDTO;
import com.jdbmAPIcore.controller.dto.UserResponseDTO;
import com.jdbmAPIcore.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "JWT")
@Tag(name="Пользователи", description="Технические методы для взаимодействия с пользователями (Доступно только с ролью [ROLE_ADMIN])")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Версия",
            description = "Получение текущей версии приложения"
    )
    @GetMapping("/version")
    public String getVersion() {
        return "0.4.0";
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping("/createUsers")
    public Long createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO.getUsername(), userRequestDTO.getPassword(), userRequestDTO.getPhone());
    }

    @Operation(
            summary = "Просмотр определенного пользователя",
            description = "Позволяет получить данные о пользователе"
    )
    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(@PathVariable @Min(1) @Parameter(description = "id пользователя") Long id) {
        return new UserResponseDTO(userService.getUserById(id));
    }

    @Operation(
            summary = "Просмотр всех пользователей",
            description = "Позволяет получить нформацию о всех пользователях, зарегистрированных в системе"
    )
    @GetMapping("/users")
    public List<UserResponseDTO> getAll() {
        return userService.getAll().stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Помечает пользователя как удаленного"
    )
    @DeleteMapping("/users/{id}")
    public UserResponseDTO delete(@PathVariable @Min(1) @Parameter(description = "id пользователя") Long id) {
        return new UserResponseDTO(userService.deleteById(id));
    }

}
