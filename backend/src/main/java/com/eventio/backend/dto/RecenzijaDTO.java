package com.eventio.backend.dto;

import com.eventio.backend.domain.Recenzija;
import jakarta.validation.constraints.NotNull;

public class RecenzijaDTO {
    @NotNull
    private Integer korisnik_id;
    @NotNull
    private Integer dogadaj_id;
    @NotNull
    private String tekst;
    private int ocjena;
    public RecenzijaDTO() {
    }

    public RecenzijaDTO(Recenzija recenzija) { 
        this.korisnik_id = recenzija.getPosjetitelj().getId();
        this.dogadaj_id = recenzija.getDogadaj().getId();
        this.tekst = recenzija.getTekst();
        this.ocjena = recenzija.getOcjena();
    }

    public Integer getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(Integer korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public Integer getDogadaj_id() {
        return dogadaj_id;
    }

    public void setDogadaj_id(Integer dogadaj_id) {
        this.dogadaj_id = dogadaj_id;
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
