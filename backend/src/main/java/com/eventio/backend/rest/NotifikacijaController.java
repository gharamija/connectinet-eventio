package com.eventio.backend.rest;


import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.dto.NotifikacijaDTO;
import com.eventio.backend.service.KorisnikService;
import com.eventio.backend.service.NotifikacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notification")
public class NotifikacijaController {

    @Autowired
    private NotifikacijaService notifikacijaService;
    @Autowired
    private KorisnikService korisnikService;
    @GetMapping("/{id}")
    public List<Notifikacija> notifikacijeKorisnika(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal Korisnik korisnik){
        if (korisnik.getId() != id)
            return null;
    return korisnik.getNotifikacije();
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> brisanje(@PathVariable(name = "id") Integer id,
                                         @AuthenticationPrincipal Korisnik korisnik){

        Optional<Notifikacija>  OptNotifikacija = notifikacijaService.findById(id);
        if (!OptNotifikacija.isPresent())
            return ResponseEntity.badRequest().body("Ne postoji notifikacija s ovim id");

            Notifikacija notifikacija = OptNotifikacija.get();

        if (notifikacija.getPosjetitelj().getId() != korisnik.getId())
            return ResponseEntity.badRequest().body("Nemate ovlasti za brisanje ove notifikacije");

        if (notifikacijaService.deleteNotifiakcija(notifikacija))
            return ResponseEntity.ok().body("Notifikacija izbrisana");
         else
            return ResponseEntity.badRequest().body("Nepoznata greška pri brisanju notifikacije");
    }
    @PostMapping("/add/{id}")
    public ResponseEntity<String> izrada(@PathVariable(name = "id") Integer id,
                                         @RequestBody NotifikacijaDTO dto,
                                         @AuthenticationPrincipal Korisnik korisnik){
        if (id != korisnik.getId())
            return ResponseEntity.badRequest().body("Nemate ovlasti za dodavanje ove notifikacije");
        try {
            Optional<Korisnik> optionalKorisnik = korisnikService.findById(id);
            if (optionalKorisnik.isPresent()) {
                Korisnik posjetitelj = optionalKorisnik.get();
                Notifikacija notifikacija = new Notifikacija(dto);
                notifikacija.setPosjetitelj(posjetitelj);
                notifikacijaService.spremi(notifikacija);
                return ResponseEntity.ok("Uspješno spremljena notifikacija.");
            } else
                return ResponseEntity.badRequest().body("Korisnik s navedenim ID-om ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja događaja.");
        }
    }
}
