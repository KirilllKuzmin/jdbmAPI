package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Токен")
public class AuthenticationResponseDTO {

    @Schema(description = "Токен")
    private String token;

    public AuthenticationResponseDTO(String token) {
        super();
        this.token = token;
    }

    public AuthenticationResponseDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}

