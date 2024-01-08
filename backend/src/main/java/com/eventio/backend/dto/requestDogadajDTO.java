package com.eventio.backend.dto;
import com.eventio.backend.domain.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class requestDogadajDTO {
    private Integer organizatorId;
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

    public requestDogadajDTO() {
    }
    public requestDogadajDTO(Dogadaj dogadaj) {
        this.nazivDogadaja = dogadaj.getNazivDogadaja();
        this.vrsta = dogadaj.getVrsta();
        this.lokacija = dogadaj.getLokacija();
        this.opisLokacije = dogadaj.getOpisLokacije();
        this.vrijemePocetka = dogadaj.getVrijemePocetka();
        this.cijenaUlaznice = dogadaj.getCijenaUlaznice();
        this.opis = dogadaj.getOpis();
        this.galerija = dogadaj.getGalerija();
    }

    public Integer getOrganizatorId() {
        return organizatorId;
    }

    public void setOrganizatorId(Integer organizatorId) {
        this.organizatorId = organizatorId;
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

}
