package com.eventio.backend.dto;

import com.eventio.backend.domain.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class responseDogadajDTO {
    @NotNull
    private Integer organizator_id;
    @NotNull
    private String username;
    @NotNull
    private Integer dogadaj_id;
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
    private List<Zainteresiranost> zainteresiranosti;
    public responseDogadajDTO(Dogadaj dogadaj) {
        this.organizator_id = dogadaj.getOrganizator().getId();
        this.username = dogadaj.getOrganizator().getUsername();
        this.dogadaj_id = dogadaj.getId();
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
        this.zainteresiranosti = dogadaj.getZainteresiranosti();
    }
    public Integer getDogadaj_id() {
        return dogadaj_id;
    }

    public void setDogadaj_id(Integer dogadaj_id) {
        this.dogadaj_id = dogadaj_id;
    }
    public Integer getOrganizator_id() {
        return organizator_id;
    }

    public void setOrganizator_id(Integer organizator_id) {
        this.organizator_id = organizator_id;
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

    public List<Zainteresiranost> getZainteresiranosti() {
        return zainteresiranosti;
    }

    public void setZainteresiranosti(List<Zainteresiranost> zainteresiranosti) {
        this.zainteresiranosti = zainteresiranosti;
    }


}
