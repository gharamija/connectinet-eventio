package com.eventio.backend.rest;

import com.eventio.backend.domain.User;
import com.eventio.backend.domain.UserType;
import com.eventio.backend.dto.UserDTO;
import com.eventio.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public UserDTO getCurrentUser(@AuthenticationPrincipal User user) {
        return new UserDTO(user);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return service.listAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {
        if (dto.getType() == UserType.ADMIN) {
            return ResponseEntity.badRequest().body("Ne možete kreirati admina");
        } else if (service.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (service.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (service.registerUser(dto)) {
            return ResponseEntity.ok().body("Korisnik kreiran");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }

}
