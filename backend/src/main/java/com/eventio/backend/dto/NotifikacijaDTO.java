package com.eventio.backend.dto;

import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Vrste;

public class NotifikacijaDTO {

    private Vrste vrsta;
    private Kvartovi lokacija;

    public NotifikacijaDTO(Vrste vrsta, Kvartovi lokacija) {
        this.vrsta = vrsta;
        this.lokacija = lokacija;
    }

    public NotifikacijaDTO() {
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
