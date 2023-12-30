package com.eventio.backend.dto;

import com.eventio.backend.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class responseDogadajDTO {
    @NotNull
    private Integer organizatorId;
    @NotNull
    private String username;
    @NotNull
    private Integer dogadajId;
    @NotNull
    private String nazivDogadaja;
    @NotNull
    private Vrste vrsta;
    @NotNull
    private Kvartovi lokacija;
    @NotNull
    private String opisLokacije;
    @NotNull
    private LocalDateTime vrijemePocetka;
    @NotNull
    private String cijenaUlaznice;
    @NotNull
    private String opis;
    @NotNull
    private String galerija;
    private List<RecenzijaDTO> recenzije;
    private Kategorija trenutna;
    private Integer sigurnoZainteresiranost;
    private Integer mozdaZainteresiranost;
    private Integer neDolazeZainteresiranost;
    public responseDogadajDTO(Dogadaj dogadaj) {
        this.organizatorId = dogadaj.getOrganizator().getId();
        this.username = dogadaj.getOrganizator().getUsername();
        this.dogadajId = dogadaj.getId();
        this.nazivDogadaja = dogadaj.getNazivDogadaja();
        this.vrsta = dogadaj.getVrsta();
        this.lokacija = dogadaj.getLokacija();
        this.opisLokacije = dogadaj.getOpisLokacije();
        this.vrijemePocetka = dogadaj.getVrijemePocetka();
        this.cijenaUlaznice = dogadaj.getCijenaUlaznice();
        this.opis = dogadaj.getOpis();
        this.galerija = dogadaj.getGalerija();
        this.recenzije = dogadaj.getRecenzije().stream()
                .map(RecenzijaDTO::new)
                .collect(Collectors.toList());
        this.trenutna = dohvatiTrenutnuZainteresiranost(dogadaj).getKategorija();
        this.mozdaZainteresiranost = countZainteresiranost(dogadaj, Kategorija.MOZDA);
        this.sigurnoZainteresiranost = countZainteresiranost(dogadaj, Kategorija.SIGURNO);
        this.neDolazeZainteresiranost = countZainteresiranost(dogadaj, Kategorija.NE);
    }

    public Integer getDogadajId() {
        return dogadajId;
    }

    public void setDogadajId(Integer dogadajId) {
        this.dogadajId = dogadajId;
    }
    public Integer getOrganizatorId() {
        return organizatorId;
    }

    public void setOrganizatorId(Integer organizatorId) {
        this.organizatorId = organizatorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public Vrste getVrsta() {
        return vrsta;
    }

    public void setVrsta(Vrste vrsta) {
        this.vrsta = vrsta;
    }

    public Kvartovi getLokacija() {
        return lokacija;
    }

    public void setLokacija(Kvartovi lokacija) {
        this.lokacija = lokacija;
    }

    public String getOpisLokacije() {
        return opisLokacije;
    }

    public void setOpisLokacije(String opisLokacije) {
        this.opisLokacije = opisLokacije;
    }

    public LocalDateTime getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(LocalDateTime vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public String getCijenaUlaznice() {
        return cijenaUlaznice;
    }

    public void setCijenaUlaznice(String cijenaUlaznice) {
        this.cijenaUlaznice = cijenaUlaznice;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getGalerija() {
        return galerija;
    }

    public void setGalerija(String galerija) {
        this.galerija = galerija;
    }

    public List<RecenzijaDTO> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(List<RecenzijaDTO> recenzije) {
        this.recenzije = recenzije;
    }

    public Kategorija getTrenutna() {
        return trenutna;
    }

    public void setTrenutna(Kategorija trenutna) {
        this.trenutna = trenutna;
    }

    public Integer getSigurnoZainteresiranost() {
        return sigurnoZainteresiranost;
    }

    public void setSigurnoZainteresiranost(Integer sigurnoZainteresiranost) {
        this.sigurnoZainteresiranost = sigurnoZainteresiranost;
    }

    public Integer getMozdaZainteresiranost() {
        return mozdaZainteresiranost;
    }

    public void setMozdaZainteresiranost(Integer mozdaZainteresiranost) {
        this.mozdaZainteresiranost = mozdaZainteresiranost;
    }
    public Integer getNeDolazeZainteresiranost() {
        return neDolazeZainteresiranost;
    }

    public void setNeDolazeZainteresiranost(Integer neDolazeZainteresiranost) {
        this.neDolazeZainteresiranost = neDolazeZainteresiranost;
    }

    private Integer countZainteresiranost(Dogadaj dogadaj, Kategorija kategorija) {
        return (int) dogadaj.getZainteresiranosti().stream()
            .filter(z -> z.getKategorija() == kategorija)
            .count();
    }

    public Zainteresiranost dohvatiTrenutnuZainteresiranost(Dogadaj dogadaj) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            Korisnik trenutniKorisnik = (Korisnik) userDetails;
            return dohvatiZainteresiranostTrenutnogKorisnika(trenutniKorisnik, dogadaj);
        }
        return null;
    }

    private Zainteresiranost dohvatiZainteresiranostTrenutnogKorisnika(Korisnik trenutniKorisnik, Dogadaj dogadaj) {
        return trenutniKorisnik.getZainteresiranosti().stream()
            .filter(z -> z.getDogadaj().equals(dogadaj))
            .findFirst()
            .orElse(null);
    }
}
