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

        return serviceDogadaj.pretvori_DTO(serviceDogadaj.listAll());
    }


    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/izrada")
    public ResponseEntity<String> izrada(@RequestParam(name = "id") Integer id, @Valid @RequestBody requestDogadajDTO dto) {
        try {
            Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(id);
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
    @GetMapping("/organizator/{id}")
    public List<responseDogadajDTO>  PrikazDogOrg(@PathVariable(name = "id") Integer id){
        Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(id);
        if (optionalOrganizator.isPresent()) {
            Organizator organizator = optionalOrganizator.get();
            Optional<List<Dogadaj>> Optionaldogadaji = serviceDogadaj.findByOrganizator(organizator);
        if (Optionaldogadaji.isPresent())
            return serviceDogadaj.pretvori_DTO(Optionaldogadaji.get());
        }
            return null;

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<String> prikazDogUsera(@PathVariable(name = "id") Integer id){
        // sve dogadaje posjetitelja
        return null;
    }
    @GetMapping("/{id}")
    public responseDogadajDTO prikaziDogadaj(@PathVariable(name = "id") Integer id){
        Optional<Dogadaj> Optionaldogadaji = serviceDogadaj.findById(id);
        if (Optionaldogadaji.isPresent()) {
            Dogadaj dogadaj = Optionaldogadaji.get();
            responseDogadajDTO odg = new responseDogadajDTO(dogadaj);
            return odg;
        }
        return null;
    }


    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/{id}")
    public ResponseEntity<String> promjeniDogadaj(@PathVariable(name = "id") Integer id){
        //promjena dogadaja
        return null;
    }

}
