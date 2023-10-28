package com.eventio.backend.service;

import com.eventio.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listAll();
    Optional<User> findById(Long id);
}
