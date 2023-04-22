package com.jdbmAPIcore.controller.dto;

import com.jdbmAPIcore.entity.User;

public class UserResponseDTO {

    private Long userId;

    private String name;

    private String login;

    private Long phone;

    public UserResponseDTO(Long userId, String name, String login, Long phone) {
        this.userId = userId;
        this.name = name;
        this.login = login;
        this.phone = phone;
    }

    public UserResponseDTO(User user) {
        userId = user.getId();
        name = user.getName();
        login = user.getLogin();
        phone = user.getPhone();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
