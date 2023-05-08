package com.jdbmAPIcore.controller.dto;

import com.jdbmAPIcore.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Schema(description = "Сущность пользователя (response)")
public class UserResponseDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Имя пользователя", example = "Имя")
    private String username;
    @Schema(description = "Пароль", example = "Пароль")
    private String password;
    @Schema(description = "Номер телефона", example = "88005553535")
    private Long phone;
    @Schema(description = "Пользователь открыт", example = "True")
    private boolean accountNonExpired;
    @Schema(description = "Пользователь не заблокирован", example = "True")
    private boolean accountNonLocked;
    @Schema(description = "Учетные данные не просрочены", example = "True")
    private boolean credentialsNonExpired;
    @Schema(description = "Пользователь активен", example = "True")
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
