package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Uloga;
import com.eventio.backend.dto.KorisnikDTO;
import com.eventio.backend.service.KorisnikService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class KorisnikController {
    @Autowired
    private KorisnikService service;

    @GetMapping
    public KorisnikDTO getCurrentUser(@AuthenticationPrincipal Korisnik korisnik) {
        return new KorisnikDTO(korisnik);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<Korisnik> getAll() {
        return service.listAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute KorisnikDTO dto) {
        if (dto.getUloga() != Uloga.POSJETITELJ) {
            return ResponseEntity.badRequest().body("Ovdje ne možete kreirati admina ili organizatora");
        } else if (service.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (service.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (service.registerUser(dto)) {
            return ResponseEntity.ok().body("Posjetitelj kreiran");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }

}
