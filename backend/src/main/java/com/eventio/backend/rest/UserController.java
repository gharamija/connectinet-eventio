package com.eventio.backend.rest;

import com.eventio.backend.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return user;
    }

}
