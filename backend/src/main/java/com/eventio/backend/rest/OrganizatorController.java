package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Uloga;
import com.eventio.backend.dto.OrganizatorDTO;
import com.eventio.backend.service.OrganizatorService;
import com.eventio.backend.service.KorisnikService;
import jakarta.validation.Valid;
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
    private KorisnikService userService;

    @Secured("ROLE_ORGANIZATOR")
    @GetMapping
    public OrganizatorDTO getDetails(@AuthenticationPrincipal Korisnik korisnik) {
        return new OrganizatorDTO(service.findById(korisnik.getId()).get());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute OrganizatorDTO dto) {
        if (dto.getUloga() != Uloga.ORGANIZATOR) {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody OrganizatorDTO dto,
                                         @AuthenticationPrincipal Korisnik trenutni) {
        if (trenutni.getId() != id)
            return ResponseEntity.badRequest().body("Ne možete promjeniti tudi racun");
        Organizator organizator = service.findById(id).get();
        if (dto.getUloga() != Uloga.ORGANIZATOR) {
            return ResponseEntity.badRequest().body("Ovdje ne možete promjeniti admina ili posjetitelja");
        } else if (!dto.getEmail().equals(organizator.getEmail()) && userService.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (!dto.getUsername().equals(organizator.getUsername()) && userService.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (service.updateOrganizator(dto,id)) {
            return ResponseEntity.ok().body("Posjetitelj promjenjen");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }
}
