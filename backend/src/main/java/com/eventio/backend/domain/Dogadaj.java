package com.eventio.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Dogadaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dogadaj_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "organizator_id", nullable = false)
    private Organizator organizator;
    @Column(nullable = false)
    private String nazivDogadaja;
    @Column(nullable = false)
    private Vrste vrsta;
    @Enumerated
    @Column(nullable = false)
    private Kvartovi lokacija;
    @Column(nullable = false)
    private String opisLokacije;
    @Column(nullable = false)
    private LocalDateTime vrijemePocetka;
    private String cijenaUlaznice;
    @Column(nullable = false)
    private String opis;
    private String galerija;
    @OneToMany(mappedBy = "dogadaj", cascade = CascadeType.ALL)
    private List<Recenzija> recenzije;
    @OneToMany(mappedBy = "dogadaj", cascade = CascadeType.ALL)
    private List<Zainteresiranost> zainteresiranosti;

    public Integer getId() {
        return id;
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

    public List<Recenzija> getRecenzije() {
        return recenzije;
    }

    public List<Zainteresiranost> getZainteresiranosti() {
        return zainteresiranosti;
    }
}
