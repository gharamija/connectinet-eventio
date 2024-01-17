package com.eventio.backend.dto;

import com.eventio.backend.domain.Recenzija;
import jakarta.validation.constraints.NotNull;

public class RecenzijaDTO {
    @NotNull
    private Integer korisnikId;
    @NotNull
    private Integer dogadajId;
    private Integer recenzijaId;
    private String username;
    @NotNull
    private String tekst;
    private int ocjena;
    public RecenzijaDTO() {
    }

    public RecenzijaDTO(Recenzija recenzija) {
        this.recenzijaId = recenzija.getId();
        this.korisnikId = recenzija.getPosjetitelj().getId();
        this.dogadajId = recenzija.getDogadaj().getId();
        this.username = recenzija.getPosjetitelj().getUsername();
        this.tekst = recenzija.getTekst();
        this.ocjena = recenzija.getOcjena();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRecenzijaId() {
        return recenzijaId;
    }

    public void setRecenzijaId(Integer recenzijaId) {
        this.recenzijaId = recenzijaId;
    }

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Integer getDogadajId() {
        return dogadajId;
    }

    public void setDogadajId(Integer dogadajId) {
        this.dogadajId = dogadajId;
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
