package com.jdbmAPIcore.controller.dto;

import javax.persistence.criteria.CriteriaBuilder;

public class UserRequestDTO {

    private String name;

    private String login;

    private String password;

    private Long phone;

    public UserRequestDTO(String name, String login, String password, Long phone) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
    }

    public UserRequestDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
