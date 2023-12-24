package com.eventio.backend.rest;
import com.eventio.backend.domain.*;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
import com.eventio.backend.service.DogadajService;
import com.eventio.backend.service.OrganizatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Comparator;
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
            @RequestParam(name = "sort", defaultValue = "vrijeme-uzlazno") String sort,
            @RequestParam(name = "lokacija", defaultValue = "") Kvartovi lokacija,
            @RequestParam(name = "vrijeme", defaultValue = "") String vrijeme,
            @RequestParam(name = "vrsta", defaultValue = "") Vrste vrsta,
            @RequestParam(name = "zavrseno", defaultValue = "") String zavrseno,
            @RequestParam(name = "placanje", defaultValue = "") String placanje){

        List<Dogadaj> sviDogađaji = serviceDogadaj.listAll();

        List<Dogadaj> filtriraniDogađaji = filtrirajDogađaje(sviDogađaji, lokacija, vrijeme, vrsta, zavrseno, placanje);

        List<Dogadaj> sortiraniDogadaji = sortirajDogađaje(filtriraniDogađaji, sort);

        return serviceDogadaj.pretvori_DTO(sortiraniDogadaji);
    }



    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/izrada")
    public ResponseEntity<String> izrada(@RequestParam(name = "id") Integer id,
                                         @Valid @RequestBody requestDogadajDTO dto,
                                         @AuthenticationPrincipal Korisnik korisnik) {
        if (id != korisnik.getId())
            return ResponseEntity.badRequest().body("Hocete stvoriti dogadaj koji neće biti u vašem vlasništvu.");

        try {
            Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(id);
            if (optionalOrganizator.isPresent()) {
                Organizator organizator = optionalOrganizator.get();
                Dogadaj dogadaj = new Dogadaj(dto);
                dogadaj.setOrganizator(organizator);
                serviceDogadaj.spremiDogadaj(dogadaj);
                // dodat u construktor dogadaju da radi provjeru ko je sve preplacen na notifikacije i salje obavjesti
                return ResponseEntity.ok("Uspješno spremljen događaj.");
            } else
                return ResponseEntity.badRequest().body("Organizator s navedenim ID-om ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja događaja.");
        }
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody requestDogadajDTO dto,
                                         @AuthenticationPrincipal Korisnik korisnik) {
        if (dto.getOrganizator().getId() != korisnik.getId())
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
    public ResponseEntity<String> prikazDogUsera(@PathVariable(name = "id") Integer id){
        // sve dogadaje posjetitelja na koji je iskazao zeinteresiranost
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


    private List<Dogadaj> filtrirajDogađaje(List<Dogadaj> sviDogađaji, Kvartovi lokacija, String vrijeme, Vrste vrsta, String zavrseno, String placanje) {
        if (lokacija != null) {
            sviDogađaji = sviDogađaji.stream()
                .filter(dogadaj -> dogadaj.getLokacija().equals(lokacija))
                .collect(Collectors.toList());
        }

        if (vrijeme != null) {
            LocalDateTime trenutnoVrijeme = LocalDateTime.now();

            switch (vrijeme) {
                case "24 sata":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(1))
                                        && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(1)))
                        .collect(Collectors.toList());
                    break;

                case "7 dana":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(7))
                                        && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(7)))
                        .collect(Collectors.toList());
                    break;

                case "30 dana":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(30))
                                        && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(30)))
                        .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }

        if (vrsta != null) {
            sviDogađaji = sviDogađaji.stream()
                .filter(dogadaj -> dogadaj.getVrsta().equals(vrsta))
                .collect(Collectors.toList());
        }

        if (zavrseno != null) {
            switch (zavrseno) {
                case "Da":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> dogadaj.getVrijemePocetka().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
                    break;
                case "Ne":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
        if (placanje != null) {
            switch (placanje) {
                case "placa se":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> Integer.parseInt(dogadaj.getCijenaUlaznice()) > 0 )
                        .collect(Collectors.toList());
                    break;
                case "besplatno":
                    sviDogađaji = sviDogađaji.stream()
                        .filter(dogadaj -> Integer.parseInt(dogadaj.getCijenaUlaznice()) == 0 )
                        .collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }
        return sviDogađaji;
    }

    private List<Dogadaj> sortirajDogađaje(List<Dogadaj> filtriraniDogađaji, String sort) {
        Comparator<Dogadaj> comparator = switch (sort) {
          case "vrijeme-silazno" -> Comparator.comparing(Dogadaj::getVrijemePocetka).reversed();
          case "zainteresiranost-uzlazno" -> Comparator.comparing(Dogadaj::zainteresiranost);
          case "zainteresiranost-silazno" -> Comparator.comparing(Dogadaj::zainteresiranost).reversed();
          default -> Comparator.comparing(Dogadaj::getVrijemePocetka);
        };

        filtriraniDogađaji.sort(comparator);

        return filtriraniDogađaji;
    }
}

