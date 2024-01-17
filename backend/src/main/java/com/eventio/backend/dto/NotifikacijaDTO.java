package com.eventio.backend.dto;

import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.domain.Vrste;

public class NotifikacijaDTO {

    private Vrste vrsta;
    private Kvartovi lokacija;
    private Integer id;

    public NotifikacijaDTO(Vrste vrsta, Kvartovi lokacija) {
        this.vrsta = vrsta;
        this.lokacija = lokacija;
    }
    public NotifikacijaDTO(Notifikacija notifikacija) {
        this.vrsta = notifikacija.getVrsta();
        this.lokacija = notifikacija.getLokacija();
        this.id = notifikacija.getId();
    }
    public NotifikacijaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
