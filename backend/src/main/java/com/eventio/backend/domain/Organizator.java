package com.eventio.backend.domain;

import com.eventio.backend.dto.OrganizatorDTO;
import com.eventio.backend.dto.UserDTO;
import jakarta.persistence.Entity;

@Entity
public class Organizator extends User {
    private String nazivOrganizacije;
    private String adresa;
    private String poveznica;
    private boolean clanarina;

    public Organizator() {
    }

    public Organizator(UserDTO dto, String nazivOrganizacije, String adresa, String poveznica, boolean clanarina) {
        super(dto);
        this.nazivOrganizacije = nazivOrganizacije;
        this.adresa = adresa;
        this.poveznica = poveznica;
        this.clanarina = clanarina;
    }

    public String getNazivOrganizacije() {
        return nazivOrganizacije;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getPoveznica() {
        return poveznica;
    }

    public boolean getClanarina() {
        return clanarina;
    }
}
