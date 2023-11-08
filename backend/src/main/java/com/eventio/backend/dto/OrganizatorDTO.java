package com.eventio.backend.dto;

import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Uloga;
import jakarta.validation.constraints.NotNull;

public class OrganizatorDTO {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Uloga uloga;
    @NotNull
    private String nazivOrganizacije;
    @NotNull
    private String adresa;
    @NotNull
    private String poveznica;
    @NotNull
    private boolean clanarina;

    public OrganizatorDTO() {
    }

    public OrganizatorDTO(Organizator organizator) {
        this.username = organizator.getUsername();
        this.email = organizator.getEmail();
        this.password = organizator.getPassword();
        this.uloga = organizator.getUloga();
        this.nazivOrganizacije = organizator.getNazivOrganizacije();
        this.adresa = organizator.getAdresa();
        this.poveznica = organizator.getPoveznica();
        this.clanarina = organizator.getClanarina();
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

    public String getNazivOrganizacije() {
        return nazivOrganizacije;
    }

    public void setNazivOrganizacije(String nazivOrganizacije) {
        this.nazivOrganizacije = nazivOrganizacije;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getPoveznica() {
        return poveznica;
    }

    public void setPoveznica(String poveznica) {
        this.poveznica = poveznica;
    }

    public boolean getClanarina() {
        return clanarina;
    }

    public void setClanarina(boolean clanarina) {
        this.clanarina = clanarina;
    }
}
