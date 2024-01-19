package com.eventio.backend.rest;

import com.eventio.backend.domain.*;
import com.eventio.backend.dto.RecenzijaDTO;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
import com.eventio.backend.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
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
    @Autowired
    private RecenzijaService serviceRecnzija;
    @Autowired
    private NotifikacijaService serviceNotifikacija; // ne brisi koristi se

    @GetMapping("/filter")
    public List<responseDogadajDTO> filter(
            @RequestParam(name = "sort", defaultValue = "vrijeme-uzlazno") String sort,
            @RequestParam(name = "lokacija", defaultValue = "") Kvartovi lokacija,
            @RequestParam(name = "vrijeme", defaultValue = "") String vrijeme,
            @RequestParam(name = "vrsta", defaultValue = "") Vrste vrsta,
            @RequestParam(name = "zavrseno", defaultValue = "") String zavrseno,
            @RequestParam(name = "placanje", defaultValue = "") String placanje) {

        List<Dogadaj> filtriraniDogadaji = serviceDogadaj.filtrirajDogadaje(serviceDogadaj.listAll(), lokacija, vrijeme,
                vrsta, zavrseno, placanje);

        List<Dogadaj> sortiraniDogadaji = serviceDogadaj.sortirajDogadaje(filtriraniDogadaji, sort);

        return serviceDogadaj.pretvori_DTO(sortiraniDogadaji);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> izrada(@PathVariable(name = "id") Integer dogadajId,
            @AuthenticationPrincipal Korisnik korisnik) {
        Optional<Dogadaj> Optionaldogadaji = serviceDogadaj.findById(dogadajId);
        if (Optionaldogadaji.isPresent()) {
            Dogadaj dogadaj = Optionaldogadaji.get();

            if (!Objects.equals(dogadaj.getOrganizator().getId(), korisnik.getId())
                    && korisnik.getUloga() != Uloga.ADMIN)
                return ResponseEntity.badRequest().body("Hocete izbrisat dogadaj koji nije u vašem vlasništvu.");

            serviceDogadaj.izbrisiDogadaj(dogadaj);
            return ResponseEntity.ok("Uspješno izbrisan događaj.");
        } else
            return ResponseEntity.badRequest().body("Ne postoji dogadaj s navedenim id-om.");
    }

    @Secured("ROLE_ORGANIZATOR")
    @PostMapping("/izrada/{id}")
    public ResponseEntity<String> izrada(@PathVariable(name = "id") Integer organizatorId,
            @Valid @RequestBody requestDogadajDTO dto,
            @AuthenticationPrincipal Korisnik korisnik) {
        if (!Objects.equals(organizatorId, korisnik.getId()))
            return ResponseEntity.badRequest().body("Hocete stvoriti dogadaj koji neće biti u vašem vlasništvu.");

        try {
            Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(organizatorId);
            if (optionalOrganizator.isPresent()) {
                Organizator organizator = optionalOrganizator.get();

                double cijena;
                try {
                    cijena = Double.parseDouble(dto.getCijenaUlaznice());
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Unesite broj za cijenu.");
                }

                if (cijena < 0) {
                    return ResponseEntity.badRequest().body("Cijena ne može biti negativna.");
                }

                // zaokruzivanje na dvije decimale
                cijena = (double) Math.round(cijena * 100) / 100;

                if (!organizator.getClanarina() && cijena != 0)
                    return ResponseEntity.badRequest().body("Organizator nema plaćenu preplatu.");

                dto.setCijenaUlaznice(String.format("%.2f", cijena));
                Dogadaj dogadaj = new Dogadaj(dto);
                dogadaj.setOrganizator(organizator);
                Dogadaj novi = serviceDogadaj.spremiDogadaj(dogadaj);
                serviceNotifikacija.posaljiNotifikacije(dogadaj.getLokacija(), dogadaj.getVrsta(),
                        dogadaj.getNazivDogadaja());
                return ResponseEntity.ok(String.valueOf(novi.getId()));
            } else
                return ResponseEntity.badRequest().body("Organizator s navedenim ID-om ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja događaja.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Integer dogadajId,
            @Valid @RequestBody requestDogadajDTO dto,
            @AuthenticationPrincipal Korisnik korisnik) {
        if (!Objects.equals(dto.getOrganizatorId(), korisnik.getId()) && korisnik.getUloga() != Uloga.ADMIN)
            return ResponseEntity.badRequest()
                    .body("Nemate ovlasti za ažuriranje ovog događaja, niste vlasnik tog dogadaja.");

        double cijena;
        try {
            cijena = Double.parseDouble(dto.getCijenaUlaznice());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Unesite broj za cijenu.");
        }

        if (cijena < 0) {
            return ResponseEntity.badRequest().body("Cijena ne može biti negativna.");
        }

        // zaokruzivanje na dvije decimale
        cijena = (double) Math.round(cijena * 100) / 100;

        if (cijena != 0
                && !serviceOrganizator.findById(dto.getOrganizatorId()).get().getClanarina()
                && korisnik.getUloga() != Uloga.ADMIN)
            return ResponseEntity.badRequest().body("Organizator nema plaćenu preplatu");

        dto.setCijenaUlaznice(String.format("%.2f", cijena));
        if (serviceDogadaj.updateDogadaj(dto, dogadajId)) {
            return ResponseEntity.ok().body("Dogadaj promjenjen");
        } else {
            return ResponseEntity.badRequest().body("Nepoznata greška");
        }

    }

    @Value("${slika.upload.dir}") // Postavite putanju za spremanje slika u application.properties
    private String uploadDir;

    @Autowired
    FileService fileService;

    @PostMapping("/slika/{dogadajId}") // treba jos istestirat
    public ResponseEntity<String> slika(
            @PathVariable(name = "dogadajId") Integer dogadajId,
            @RequestPart("file") MultipartFile slika) {
        if (slika.isEmpty()) {
            return ResponseEntity.badRequest().body("Molimo, odaberite sliku");
        }

        String newName = null;
        try {
            newName = fileService.upload(slika);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Dogodila se pogreška u spremanju slike");
        }

        // Ažurirajte galeriju u Dogadaj objektu
        Optional<Dogadaj> OptDogadaj = serviceDogadaj.findById(dogadajId);
        if (OptDogadaj.isPresent()) {
            Dogadaj dogadaj = OptDogadaj.get();
            dogadaj.setGalerija(newName);
            serviceDogadaj.spremiDogadaj(dogadaj);
        }

        return ResponseEntity.ok("Slika uspješno spremljena");
    }

    @GetMapping("/organizator/{id}")
    public List<responseDogadajDTO> PrikazDogOrg(@PathVariable(name = "id") Integer organizatorId) {
        Optional<Organizator> optionalOrganizator = serviceOrganizator.findById(organizatorId);
        if (optionalOrganizator.isPresent()) {
            Organizator organizator = optionalOrganizator.get();
            Optional<List<Dogadaj>> Optionaldogadaji = serviceDogadaj.findByOrganizator(organizator);
            if (Optionaldogadaji.isPresent())
                return serviceDogadaj.pretvori_DTO(Optionaldogadaji.get());
        }
        return null;
    }

    @GetMapping("/user/{id}")
    public List<responseDogadajDTO> prikazDogUsera(@PathVariable(name = "id") Integer korisnikId,
            @RequestParam(name = "vrijeme", required = false) String vrijemeFilter) {
        Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(korisnikId);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            Optional<List<Zainteresiranost>> zainteresiranosti = Optional
                    .ofNullable(serviceZainteresiranost.findByPosjetiteljAndKategorijaIn(
                            korisnik,
                            Arrays.asList(Kategorija.MOZDA, Kategorija.SIGURNO)));

            if (zainteresiranosti.isPresent()) {
                Optional<List<Dogadaj>> OptreagiraniDogadaji = Optional.of(zainteresiranosti.stream()
                        .flatMap(List::stream)
                        .map(Zainteresiranost::getDogadaj)
                        .collect(Collectors.toList()));

                if (OptreagiraniDogadaji.isPresent()) {
                    List<Dogadaj> reagiraniDogadaji = OptreagiraniDogadaji.get();
                    if ("svrseni".equalsIgnoreCase(vrijemeFilter)) {
                        reagiraniDogadaji = serviceDogadaj.filtrirajDogadaje(reagiraniDogadaji, null, null, null, "Da",
                                null);
                    } else if ("nesvrseni".equalsIgnoreCase(vrijemeFilter)) {
                        reagiraniDogadaji = serviceDogadaj.filtrirajDogadaje(reagiraniDogadaji, null, null, null, "Ne",
                                null);
                    }
                    return serviceDogadaj.pretvori_DTO(reagiraniDogadaji);
                }
            }
        }
        return null;
    }

    @GetMapping("/{id}")
    public responseDogadajDTO prikaziDogadaj(@PathVariable(name = "id") Integer id) {
        Optional<Dogadaj> Optionaldogadaji = serviceDogadaj.findById(id);
        if (Optionaldogadaji.isPresent()) {
            Dogadaj dogadaj = Optionaldogadaji.get();
            return new responseDogadajDTO(dogadaj);
        }
        return null;
    }

    @PostMapping("/zainteresiranost")
    public ResponseEntity<String> stvoriZainteresitarnost(
            @RequestParam(name = "dogadajId", defaultValue = "") Integer dogadajId,
            @RequestParam(name = "korisnikId", defaultValue = "") Integer korisnikId,
            @RequestParam(name = "kategorija", defaultValue = "") Kategorija kategorija,
            @AuthenticationPrincipal Korisnik korisnikAut) {
        if (!Objects.equals(korisnikId, korisnikAut.getId()))
            return ResponseEntity.badRequest().body("Hocete stvoriti zainteresiranost za osobu koja niste vi.");
        try {
            Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(korisnikId);
            Optional<Dogadaj> optionalDogadaji = serviceDogadaj.findById(dogadajId);
            if (optionalKorisnik.isPresent() && optionalDogadaji.isPresent()) {
                Korisnik korisnik = optionalKorisnik.get();
                Dogadaj dogadaj = optionalDogadaji.get();
                serviceZainteresiranost.spremiZainteresiranost(korisnik, dogadaj, kategorija);
                return ResponseEntity.ok("Uspješno spremljena zainteresiranost.");
            } else
                return ResponseEntity.badRequest().body("Korisnik ili dogadaj s navedenim id ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja zainteresiranosti.");
        }
    }

    @PostMapping("/recenzija")
    public ResponseEntity<String> stvoriRecenziju(@Valid @RequestBody RecenzijaDTO dto) {
        if (dto.getTekst().length() >= 200)
            return ResponseEntity.badRequest()
                    .body("Prevelika recenzija, ima više od 200 znakova. Broj znakova je" + dto.getTekst().length());

        try {
            Optional<Korisnik> optionalKorisnik = serviceKorisnik.findById(dto.getKorisnikId());
            Optional<Dogadaj> optionalDogadaji = serviceDogadaj.findById(dto.getDogadajId());
            if (optionalKorisnik.isPresent() && optionalDogadaji.isPresent()) {
                Korisnik korisnik = optionalKorisnik.get();
                Dogadaj dogadaj = optionalDogadaji.get();
                serviceRecnzija.spremiRecenziju(korisnik, dogadaj, dto);
                return ResponseEntity.ok("Uspješno spremljena recenzija.");
            } else
                return ResponseEntity.badRequest().body("Korisnik ili dogadaj s navedenim id ne postoji.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Greška prilikom spremanja recenzije.");
        }
    }

}
