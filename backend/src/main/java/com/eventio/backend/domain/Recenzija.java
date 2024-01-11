package com.eventio.backend.domain;

import com.eventio.backend.dto.RecenzijaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recenzijaId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "posjetiteljId", nullable = false)
    private Korisnik posjetitelj;
    @ManyToOne
    @JoinColumn(name = "dogadajId", nullable = false)
    private Dogadaj dogadaj;
    @Column(nullable = false)
    private String tekst;
    @Min(1)
    @Max(5)
    private int ocjena;
    public Recenzija(){}
    public Recenzija(RecenzijaDTO dto, Korisnik posjetitelj, Dogadaj dogadaj) {
        this.posjetitelj = posjetitelj;
        this.dogadaj = dogadaj;
        this.tekst = dto.getTekst();
        this.ocjena = dto.getOcjena();
    }

    public Integer getId() {
        return id;
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
