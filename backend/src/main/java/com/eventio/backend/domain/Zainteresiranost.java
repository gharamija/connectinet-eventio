package com.eventio.backend.domain;

import jakarta.persistence.*;

@Entity
public class Zainteresiranost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zainteresiranostId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "posjetiteljId", nullable = false)
    private Korisnik posjetitelj;
    @ManyToOne
    @JoinColumn(name = "dogadajId", nullable = false)
    private Dogadaj dogadaj;
    @Enumerated
    @Column(nullable = false)
    private Kategorija kategorija;

    public Zainteresiranost(Korisnik posjetitelj, Dogadaj dogadaj, Kategorija kategorija) {
        this.posjetitelj = posjetitelj;
        this.dogadaj = dogadaj;
        this.kategorija = kategorija;
    }

    public Zainteresiranost() {
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

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

}
