package com.eventio.backend.dto;

public class NotifikacijaDTO {

    private String vrsta;
    private String lokacija;

    public NotifikacijaDTO(String vrsta, String lokacija) {
        this.vrsta = vrsta;
        this.lokacija = lokacija;
    }

    public NotifikacijaDTO() {
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}
