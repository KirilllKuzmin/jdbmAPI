package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Schema(description = "Сущность пользователя (reuest)")
public class UserRequestDTO {

    @Schema(description = "Имя пользователя", example = "Имя")
    private String username;
    @Schema(description = "Пароль", example = "Пароль")
    private String password;
    @Pattern(regexp = "[0-9]{11}")
    @Schema(description = "Номер телефона", example = "Номер телефона")
    private Long phone;

    public UserRequestDTO(String password, String username, Long phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public UserRequestDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
