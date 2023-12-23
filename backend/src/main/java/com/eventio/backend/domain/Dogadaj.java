package com.eventio.backend.domain;

import com.eventio.backend.dto.requestDogadajDTO;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JsonIgnore
    private List<Recenzija> recenzije;
    @OneToMany(mappedBy = "dogadaj", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Zainteresiranost> zainteresiranosti;

    public Dogadaj(requestDogadajDTO dto){
        this.recenzije = new ArrayList<>();
        this.zainteresiranosti = new ArrayList<>();
        this.nazivDogadaja = dto.getNazivDogadaja();
        this.vrsta = dto.getVrsta();
        this.lokacija = dto.getLokacija();
        this.opisLokacije = dto.getOpisLokacije();
        this.vrijemePocetka = dto.getVrijemePocetka();
        this.cijenaUlaznice = dto.getCijenaUlaznice();
        this.opis = dto.getOpis();
        this.galerija = dto.getGalerija();
    }
    public Dogadaj(){}
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

    public long zainteresiranost() {
        return zainteresiranosti.stream()
            .filter(zainteresiranost -> zainteresiranost.getKategorija() == Kategorija.SIGURNO)
            .count();
    }
}
