package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Uloga;
import com.eventio.backend.dto.responseOrganizatorDTO;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.dto.responseKorisnikDTO;
import com.eventio.backend.service.KorisnikService;
import com.eventio.backend.service.impl.OrganizatorServiceJpa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class KorisnikController {

    @Autowired
    private KorisnikService serviceKorisnik;
    @Autowired
    private OrganizatorServiceJpa organizatorService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAll() {
        List<Object> korisnici = new ArrayList<>();

        List<Korisnik> sviKorisnici = serviceKorisnik.listAll();
        for (Korisnik korisnik : sviKorisnici) {
            if (korisnik.getUloga() == Uloga.ORGANIZATOR) {
                Optional<Organizator> organizatorOptional = organizatorService.findById(korisnik.getId());
                organizatorOptional.ifPresent(organizator -> korisnici.add(new responseOrganizatorDTO(organizator)));
            } else {
                korisnici.add(new responseKorisnikDTO(korisnik));
            }
        }

        return ResponseEntity.ok(korisnici);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Integer korisnikId) {
        Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(korisnikId);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            serviceKorisnik.deleteUser(korisnik);
            return ResponseEntity.ok("Uspješno izbrisan korisnik.");
        }else {
            return ResponseEntity.badRequest().body("Ne postoji korisnik s navedenim id-om.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute requestKorisnikDTO dto) {
        if (dto.getUloga() != Uloga.POSJETITELJ) {
            return ResponseEntity.badRequest().body("Ovdje ne možete kreirati admina ili organizatora");
        } else if (serviceKorisnik.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (serviceKorisnik.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (serviceKorisnik.registerUser(dto)) {
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

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody requestKorisnikDTO dto,
                                         @AuthenticationPrincipal Korisnik trenutni) {
        if (!Objects.equals(trenutni.getId(), id)  && trenutni.getUloga() != Uloga.ADMIN)
            return ResponseEntity.badRequest().body("Ne možete promjeniti tudi racun");
        Korisnik korisnik = serviceKorisnik.findById(id).get();
        if (dto.getUloga() != Uloga.POSJETITELJ) {
            return ResponseEntity.badRequest().body("Ovdje ne možete promjeniti admina ili organizatora");
        } else if (!dto.getEmail().equals(korisnik.getEmail()) && serviceKorisnik.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email već postoji u bazi");
        } else if (!dto.getUsername().equals(korisnik.getUsername()) && serviceKorisnik.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username već postoji u bazi");
        } else if (serviceKorisnik.updateUser(dto,id)) {
            return ResponseEntity.ok().body("Posjetitelj promjenjen");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }
    }
}
