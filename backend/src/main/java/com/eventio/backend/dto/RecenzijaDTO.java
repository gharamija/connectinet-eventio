package com.eventio.backend.dto;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Recenzija;
import jakarta.validation.constraints.NotNull;

public class RecenzijaDTO {
    @NotNull
    private Korisnik posjetitelj;
    @NotNull
    private Dogadaj dogadaj;
    @NotNull
    private String tekst;
    private int ocjena;
    public RecenzijaDTO() {
    }

    public RecenzijaDTO(Recenzija recenzija) {
        this.posjetitelj = recenzija.getPosjetitelj();
        this.dogadaj = recenzija.getDogadaj();
        this.tekst = recenzija.getTekst();
        this.ocjena = recenzija.getOcjena();
    }

    public Korisnik getPosjetitelj() {
        return posjetitelj;
    }

    public void setPosjetitelj(Korisnik posjetitelj) {
        this.posjetitelj = posjetitelj;
    }

    public Dogadaj getDogadaj() {
        return dogadaj;
    }

    public void setDogadaj(Dogadaj dogadaj) {
        this.dogadaj = dogadaj;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }
}
