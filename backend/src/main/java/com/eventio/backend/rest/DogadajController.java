package com.eventio.backend.rest;
import com.eventio.backend.domain.*;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
import com.eventio.backend.service.DogadajService;
import com.eventio.backend.service.KorisnikService;
import com.eventio.backend.service.OrganizatorService;
import com.eventio.backend.service.ZainteresiranostService;
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
// ne znam dal negdje treba dodat provjere dal je logiran korisik ili ce to front odradit
    @Autowired
    private DogadajService serviceDogadaj;
    @Autowired
    private OrganizatorService serviceOrganizator;
    @Autowired
    private KorisnikService serviceKorisnik;
    @Autowired
    private ZainteresiranostService serviceZainteresiranost;

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
    @PostMapping("/zaiteresiranost")
    public ResponseEntity<String> stvoriZainteresitarnost(
            @RequestParam(name = "id_dogadaj", defaultValue = "") Integer id_dogadaj,
            @RequestParam(name = "id_korisnik", defaultValue = "") Integer id_korisnik,
            @RequestParam(name = "kategorija", defaultValue = "") Kategorija kategorija){
      try {
            Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(id_korisnik);
            Optional<Dogadaj> optionalDogadaji = serviceDogadaj.findById(id_dogadaj);
            if (optionalKorisnik.isPresent() && optionalDogadaji.isPresent()) {
                Korisnik korisnik = optionalKorisnik.get();
                Dogadaj dogadaj = optionalDogadaji.get();
                //dodat provjeru ako Zainteresiranost vec postoji da se editat samo, ovo stvara novu
                serviceZainteresiranost.spremiZainteresiranost(new Zainteresiranost(korisnik,dogadaj,kategorija));
                return ResponseEntity.ok("Uspješno spremljena zainteresiranost.");
            } else
                return ResponseEntity.badRequest().body("Korisnik ili dogadaj s navedenim id ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja zainteresiranosti.");
        }

    }
    @PostMapping("/obavijest")
    public ResponseEntity<String> stvoriObavijest(
            @RequestParam(name = "id_dogadaj", defaultValue = "") Integer id_dogadaj,
            @RequestParam(name = "id_korisnik", defaultValue = "") Integer id_korisnik) {
        return null;
    }
    @PostMapping("/recenzija")
    public ResponseEntity<String> stvoriRecenziju(){
        return null;
    }
    @PostMapping("/edit")
    public ResponseEntity<String> EditDogadaja(){
        return null;
    }
}
