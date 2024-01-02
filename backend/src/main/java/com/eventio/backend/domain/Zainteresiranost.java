package com.eventio.backend.domain;

import jakarta.persistence.*;

@Entity
public class Zainteresiranost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zainteresiranost_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "posjetitelj_id", nullable = false)
    private Korisnik posjetitelj;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dogadaj_id", nullable = false)
    private Dogadaj dogadaj;
    @Enumerated
    @Column(nullable = false)
    private Kategorija kategorija;
    @Column(nullable = false)
    private boolean obavijesti;

    public Zainteresiranost(Korisnik posjetitelj, Dogadaj dogadaj, Kategorija kategorija) {
        this.posjetitelj = posjetitelj;
        this.dogadaj = dogadaj;
        this.kategorija = kategorija;
        // Dodaj zainteresiranost korisniku
        posjetitelj.getZainteresiranosti().add(this);
        // Dodaj zainteresiranost događaju
        dogadaj.getZainteresiranosti().add(this);
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

    public boolean isObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(boolean obavijesti) {
        this.obavijesti = obavijesti;
    }
}
