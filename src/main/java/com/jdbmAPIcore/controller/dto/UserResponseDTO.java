package com.jdbmAPIcore.controller.dto;

import com.jdbmAPIcore.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class UserResponseDTO {

    private Long userId;

    private String username;
    private String password;
    private Long phone;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public UserResponseDTO(Long userId, String username, Long phone, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public UserResponseDTO(User accountById) {
        this.userId = accountById.getId();
        this.username = accountById.getUsername();
        this.phone = accountById.getPhone();
        this.accountNonExpired = accountById.isAccountNonExpired();
        this.accountNonLocked = accountById.isAccountNonLocked();
        this.credentialsNonExpired = accountById.isCredentialsNonExpired();
        this.enabled = accountById.isEnabled();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
