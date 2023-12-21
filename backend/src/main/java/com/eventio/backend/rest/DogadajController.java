package com.eventio.backend.rest;
import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Vrste;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
import com.eventio.backend.service.DogadajService;
import com.eventio.backend.service.OrganizatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dogadaj")
public class DogadajController {
    // može ic ako se odlucimo i na posebno korisnika posebno orgaizatora,
    // ali mislim da pocetak bolje ovako da front može prije pocet
    // takoder ovo mozda razbit na neke manje funckije

    @Autowired
    private DogadajService serviceDogadaj;
    @Autowired
    private OrganizatorService serviceOrganizator;

    @GetMapping("/filter")
    public List<responseDogadajDTO>  filter(
            @RequestParam(name = "sort", defaultValue = "uzlazno") String sort,
            @RequestParam(name = "lokacija", defaultValue = "") Kvartovi lokacija,
            @RequestParam(name = "vrijeme", defaultValue = "") String vrijeme,
            @RequestParam(name = "vrsta", defaultValue = "") Vrste vrsta,
            @RequestParam(name = "zavrseno", defaultValue = "") Integer zavrseno,
            @RequestParam(name = "placanje", defaultValue = "") Integer placanje){

        return serviceDogadaj.vratiDogadaje();
    }


    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/izrada")
    public ResponseEntity<String> izrada(@Valid @RequestBody requestDogadajDTO dto) {
        try {
            Optional<Organizator> optionalOrganizator = serviceOrganizator.findById((dto.getOrganizator()).getId());
            if (optionalOrganizator.isPresent()) {
                Organizator organizator = optionalOrganizator.get();
                Dogadaj dogadaj = new Dogadaj(dto);
                dogadaj.setOrganizator(organizator);
                serviceDogadaj.spremiDogadaj(dogadaj);
                return ResponseEntity.ok("Uspješno spremljen događaj.");
            } else
                return ResponseEntity.badRequest().body("Organizator s navedenim ID-om ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja događaja.");
        }
    }


}
