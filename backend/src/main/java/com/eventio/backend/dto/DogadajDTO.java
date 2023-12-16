package com.eventio.backend.dto;
import com.eventio.backend.domain.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class DogadajDTO {
    @NotNull
    private Organizator organizator;
    @NotNull
    private String nazivDogadaja;
    @NotNull
    private Vrste vrsta;
    @NotNull
    private Kvartovi lokacija;
    @NotNull
    private String opisLokacije;
    @NotNull
    private String vrijemePocetka;
    @NotNull
    private String cijenaUlaznice;
    @NotNull
    private String opis;
    @NotNull
    private String galerija;
    @NotNull
    private List<Recenzija> recenzije;
    @NotNull
    private List<Zainteresiranost> zainteresiranosti;
    public DogadajDTO() {
    }
    public DogadajDTO(Dogadaj dogadaj) {
        this.organizator = dogadaj.getOrganizator();
        this.nazivDogadaja = dogadaj.getNazivDogadaja();
        this.vrsta = dogadaj.getVrsta();
        this.lokacija = dogadaj.getLokacija();
        this.opisLokacije = dogadaj.getOpisLokacije();
        this.vrijemePocetka = dogadaj.getVrijemePocetka();
        this.cijenaUlaznice = dogadaj.getCijenaUlaznice();
        this.opis = dogadaj.getOpis();
        this.galerija = dogadaj.getGalerija();
        this.recenzije = dogadaj.getRecenzije();
        this.zainteresiranosti = dogadaj.getZainteresiranosti();
    }

    public Organizator getOrganizator() {
        return organizator;
    }

    public void setOrganizator(Organizator organizator) {
        this.organizator = organizator;
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

    public String getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(String vrijemePocetka) {
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

    public List<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(List<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

    public List<Zainteresiranost> getZainteresiranosti() {
        return zainteresiranosti;
    }

    public void setZainteresiranosti(List<Zainteresiranost> zainteresiranosti) {
        this.zainteresiranosti = zainteresiranosti;
    }
}
