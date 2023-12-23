package com.eventio.backend.domain;

import com.eventio.backend.dto.requestKorisnikDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "organizator_id")
public class Organizator extends Korisnik {
    @Column(nullable = false)
    private String nazivOrganizacije;
    @Column(nullable = false)
    private String adresa;
    @Column(nullable = false)
    private String poveznica;
    @Column(nullable = false)
    private boolean clanarina;
    @JsonIgnore
    @OneToMany(mappedBy = "organizator")
    private List<Dogadaj> dogadaji;

    public Organizator() {
    }

    public Organizator(requestKorisnikDTO dto, String nazivOrganizacije, String adresa, String poveznica, boolean clanarina) {
        super(dto);
        this.nazivOrganizacije = nazivOrganizacije;
        this.adresa = adresa;
        this.poveznica = poveznica;
        this.clanarina = clanarina;
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

    public List<Dogadaj> getDogadaji() {
        return dogadaji;
    }
}
