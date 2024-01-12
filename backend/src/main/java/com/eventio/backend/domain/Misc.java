package com.eventio.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Misc {
    @Id
    private String ime;

    private String vrijednost;

    public Misc(String ime, String vrijednost) {
        this.ime = ime;
        this.vrijednost = vrijednost;
    }

    public Misc() {
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }
}
