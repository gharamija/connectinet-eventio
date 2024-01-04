package com.eventio.backend.domain;

import com.eventio.backend.dto.NotifikacijaDTO;
import jakarta.persistence.*;

@Entity
public class Notifikacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notifikacija_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "posjetitelj_id", nullable = false)
    private Korisnik posjetitelj;

    private Vrste vrsta;
    private Kvartovi lokacija;

    public Notifikacija(NotifikacijaDTO dto) {
        this.vrsta = dto.getVrsta();
        this.lokacija = dto.getLokacija();
    }

    public Notifikacija() {
    }

    public Korisnik getPosjetitelj() {
        return posjetitelj;
    }

    public void setPosjetitelj(Korisnik posjetitelj) {
        posjetitelj.getNotifikacije().add(this);
        this.posjetitelj = posjetitelj;
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

    public Integer getId() {
        return id;
    }
}
