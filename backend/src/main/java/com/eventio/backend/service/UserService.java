package com.eventio.backend.service;

import com.eventio.backend.domain.User;
import com.eventio.backend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> listAll();
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    public boolean registerUser(UserDTO dto);
}
