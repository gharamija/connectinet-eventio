package com.eventio.backend.dto;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Uloga;
import jakarta.validation.constraints.NotNull;

public class requestKorisnikDTO {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Uloga uloga;

    public requestKorisnikDTO() {
    }

    public requestKorisnikDTO(Korisnik korisnik) {
        this.username = korisnik.getUsername();
        this.email = korisnik.getEmail();
        this.uloga = korisnik.getUloga();
    }

    public requestKorisnikDTO(OrganizatorDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.uloga = dto.getUloga();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
