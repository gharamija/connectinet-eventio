package com.eventio.backend.dto;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Uloga;
import jakarta.validation.constraints.NotNull;

public class responseKorisnikDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private Uloga uloga;

    public responseKorisnikDTO() {
    }

    public responseKorisnikDTO(Korisnik korisnik) {
        this.id = korisnik.getId();
        this.username = korisnik.getUsername();
        this.email = korisnik.getEmail();
        this.uloga = korisnik.getUloga();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
