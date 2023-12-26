package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Uloga;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.dto.responseKorisnikDTO;
import com.eventio.backend.service.KorisnikService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class KorisnikController {
    @Autowired
    private KorisnikService service;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<Korisnik> getAll() {
        return service.listAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute requestKorisnikDTO dto) {
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

    @GetMapping
    public ResponseEntity<responseKorisnikDTO> validate(@AuthenticationPrincipal Korisnik korisnik) {
        if (korisnik != null) {
            responseKorisnikDTO korisnikDTO = new responseKorisnikDTO(korisnik);
            return ResponseEntity.ok(korisnikDTO);
        }  else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody requestKorisnikDTO dto,
                                         @AuthenticationPrincipal Korisnik trenutni) {
        if (trenutni.getId() != id)
            return ResponseEntity.badRequest().body("Ne možete promjeniti tudi racun");
        Korisnik korisnik = service.findById(id).get();
        if (dto.getUloga() != Uloga.POSJETITELJ) {
            return ResponseEntity.badRequest().body("Ovdje ne možete promjeniti admina ili organizatora");
        } else if (!dto.getEmail().equals(korisnik.getEmail()) && service.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (!dto.getUsername().equals(korisnik.getUsername()) && service.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (service.updateUser(dto,id)) {
            return ResponseEntity.ok().body("Posjetitelj promjenjen");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }

}
