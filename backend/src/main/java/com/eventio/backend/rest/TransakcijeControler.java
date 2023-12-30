package com.eventio.backend.rest;

import com.eventio.backend.domain.Organizator;
import com.eventio.backend.service.OrganizatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/transakcija")
public class TransakcijeControler {
private OrganizatorService organizatorService;
    @PostMapping("/paypal/{id}")
    public ResponseEntity<String> PlacanjePayPal(@PathVariable(name = "id") Integer id){
        return Plati(95,id);
    }

    @PostMapping("/banka/{id}")
    public ResponseEntity<String> PlacanjeBanka(@PathVariable(name = "id") Integer id){
        return Plati(90,id);
    }

    private ResponseEntity<String> Plati(Integer postotak, Integer id){
        Optional<Organizator> OptOrganizator = organizatorService.findById(id);
        if (OptOrganizator.isPresent()){
            Organizator organizator = OptOrganizator.get();
            if (organizator.getClanarina())
                return ResponseEntity.badRequest().body("Korisnik već ima plaćenu clanarinu");
            int randomBroj = new Random().nextInt(101);
            if (randomBroj < postotak) {
                organizator.setClanarina(true);
                organizatorService.Spremi(organizator);
                return ResponseEntity.ok().body("Placanje uspjesno");
            }else {
                return ResponseEntity.badRequest().body("Transakcija nije uspjela");
            }
        }
        return ResponseEntity.badRequest().body("Nepoznata greška");

    }
}
