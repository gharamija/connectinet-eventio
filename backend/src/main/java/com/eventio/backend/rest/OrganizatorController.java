package com.eventio.backend.rest;

import com.eventio.backend.domain.User;
import com.eventio.backend.domain.UserType;
import com.eventio.backend.dto.OrganizatorDTO;
import com.eventio.backend.service.OrganizatorService;
import com.eventio.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizator")
public class OrganizatorController {

    @Autowired
    private OrganizatorService service;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ORGANIZATOR")
    @GetMapping
    public OrganizatorDTO getDetails(@AuthenticationPrincipal User user) {
        return new OrganizatorDTO(service.findById(user.getId()).get());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody OrganizatorDTO dto) {
        if (dto.getType() != UserType.ORGANIZATOR) {
            return ResponseEntity.badRequest().body("Ovdje možete kreirati admina ili posjetitelja");
        } else if (userService.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (userService.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (service.registerOrganizator(dto)) {
            return ResponseEntity.ok().body("Organizator kreiran");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }
}
