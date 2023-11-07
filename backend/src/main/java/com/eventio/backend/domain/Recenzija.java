package com.eventio.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recenzija_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "posjetitelj_id", nullable = false)
    private Korisnik posjetitelj;
    @ManyToOne
    @JoinColumn(name = "dogadaj_id", nullable = false)
    private Dogadaj dogadaj;
    @Column(nullable = false)
    private String tekst;
    @Min(1)
    @Max(10)
    private int ocjena;

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
