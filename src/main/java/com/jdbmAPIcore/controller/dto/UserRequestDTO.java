package com.jdbmAPIcore.controller.dto;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.Set;

public class UserRequestDTO {

    private String username;
    private String password;
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
