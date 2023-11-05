package com.eventio.backend.dto;

import com.eventio.backend.domain.User;
import com.eventio.backend.domain.UserType;

public class UserDTO {
    private String username;
    private String email;
    private String password;
    private UserType type;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = "Should not be visible";
        this.type = user.getType();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
