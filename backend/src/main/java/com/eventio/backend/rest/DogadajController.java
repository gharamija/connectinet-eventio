package com.eventio.backend.rest;
import com.eventio.backend.domain.*;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
import com.eventio.backend.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dogadaj")
public class DogadajController {
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
            @RequestParam(name = "sort", defaultValue = "vrijeme-uzlazno") String sort,
            @RequestParam(name = "lokacija", defaultValue = "") Kvartovi lokacija,
            @RequestParam(name = "vrijeme", defaultValue = "") String vrijeme,
            @RequestParam(name = "vrsta", defaultValue = "") Vrste vrsta,
            @RequestParam(name = "zavrseno", defaultValue = "") String zavrseno,
            @RequestParam(name = "placanje", defaultValue = "") String placanje){

        List<Dogadaj> filtriraniDogađaji = serviceDogadaj.filtrirajDogađaje(serviceDogadaj.listAll(), lokacija, vrijeme, vrsta, zavrseno, placanje);

        List<Dogadaj> sortiraniDogadaji = serviceDogadaj.sortirajDogađaje(filtriraniDogađaji, sort);

        return serviceDogadaj.pretvori_DTO(sortiraniDogadaji);
    }
    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/izrada/{id}")
    public ResponseEntity<String> izrada(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody requestDogadajDTO dto,
                                         @AuthenticationPrincipal Korisnik korisnik) {
        if (id != korisnik.getId())
            return ResponseEntity.badRequest().body("Hocete stvoriti dogadaj koji neće biti u vašem vlasništvu.");

        try {
            Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(id);
            if (optionalOrganizator.isPresent()) {
                Organizator organizator = optionalOrganizator.get();
                if (!organizator.getClanarina() && dto.getCijenaUlaznice().equals("0"))
                    return ResponseEntity.badRequest().body("Organizator nema plaćenu preplatu");

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
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody requestDogadajDTO dto,
                                         @AuthenticationPrincipal Korisnik korisnik) {
        if (dto.getOrganizator().getId() != korisnik.getId() && korisnik.getUloga() != Uloga.ADMIN )
            return ResponseEntity.badRequest().body("Nemate ovlasti za ažuriranje ovog događaja, niste vlasnik tog dogadaja.");

        if (serviceDogadaj.updateDogadaj(dto,id)) {
            return ResponseEntity.ok().body("Dogadaj promjenjen");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
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
    public List<responseDogadajDTO> prikazDogUsera(@PathVariable(name = "id") Integer id,
                                                   @RequestParam(name = "vrijeme", required = false) String vrijemeFilter){
        Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(id);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            Optional<List<Zainteresiranost>> zainteresiranosti = Optional.ofNullable(serviceZainteresiranost.findByPosjetiteljAndKategorijaIn(
                korisnik,
                Arrays.asList(Kategorija.MOZDA, Kategorija.SIGURNO)
            ));

            if (zainteresiranosti.isPresent()) {
                Optional<List<Dogadaj>> OptreagiraniDogadaji = Optional.of(zainteresiranosti.stream()
                    .flatMap(List::stream)
                    .map(Zainteresiranost::getDogadaj)
                    .collect(Collectors.toList()));

                if (OptreagiraniDogadaji.isPresent()){
                    List<Dogadaj> reagiraniDogadaji = OptreagiraniDogadaji.get();
                    if ("svrseni".equalsIgnoreCase(vrijemeFilter)) {
                        reagiraniDogadaji = serviceDogadaj.filtrirajDogađaje(reagiraniDogadaji, null,null,null,"Da",null);
                    } else if ("nesvrseni".equalsIgnoreCase(vrijemeFilter)) {
                        reagiraniDogadaji = serviceDogadaj.filtrirajDogađaje(reagiraniDogadaji, null,null,null,"Ne",null);
                    }
                    return serviceDogadaj.pretvori_DTO(reagiraniDogadaji);
                }
            }
        }
        return null;
    }
    @GetMapping("/{id}")
    public responseDogadajDTO prikaziDogadaj(@PathVariable(name = "id") Integer id){
        Optional<Dogadaj> Optionaldogadaji = serviceDogadaj.findById(id);
        if (Optionaldogadaji.isPresent()) {
            Dogadaj dogadaj = Optionaldogadaji.get();
          return new responseDogadajDTO(dogadaj);
        }
        return null;
    }
    @PostMapping("/zaiteresiranost")
    public ResponseEntity<String> stvoriZainteresitarnost(
            @RequestParam(name = "id_dogadaj", defaultValue = "") Integer id_dogadaj,
            @RequestParam(name = "id_korisnik", defaultValue = "") Integer id_korisnik,
            @RequestParam(name = "kategorija", defaultValue = "") Kategorija kategorija,
            @AuthenticationPrincipal Korisnik korisnikAut) {
        if (id_korisnik != korisnikAut.getId())
            return ResponseEntity.badRequest().body("Hocete stvoriti zainteresiranost za osobu koja niste vi.");
      try {
            Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(id_korisnik);
            Optional<Dogadaj> optionalDogadaji = serviceDogadaj.findById(id_dogadaj);
            if (optionalKorisnik.isPresent() && optionalDogadaji.isPresent()) {
                Korisnik korisnik = optionalKorisnik.get();
                Dogadaj dogadaj = optionalDogadaji.get();
                serviceZainteresiranost.spremiZainteresiranost(korisnik,dogadaj,kategorija);
                return ResponseEntity.ok("Uspješno spremljena zainteresiranost.");
            } else
                return ResponseEntity.badRequest().body("Korisnik ili dogadaj s navedenim id ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja zainteresiranosti.");
        }
    }
    @PostMapping("/recenzija")
    public ResponseEntity<String> stvoriRecenziju(){
        return null;
    }


}

