package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Аунтентификация")
public class AuthenticationRequestDTO {

    @Schema(description = "Имя пользователя", example = "Имя")
    private String username;
    @Schema(description = "Пароль", example = "Пароль")
    private String password;


    public AuthenticationRequestDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
