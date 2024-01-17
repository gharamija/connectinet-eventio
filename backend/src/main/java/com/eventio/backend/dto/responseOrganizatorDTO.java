package com.eventio.backend.dto;

import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Uloga;
import jakarta.validation.constraints.NotNull;

public class responseOrganizatorDTO {
  @NotNull
  private Integer organizator_id;
  @NotNull
  private String username;
  @NotNull
  private String email;
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

  public responseOrganizatorDTO() {
  }

  public responseOrganizatorDTO(Organizator organizator) {
    this.organizator_id = organizator.getId();
    this.username = organizator.getUsername();
    this.email = organizator.getEmail();
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

  public boolean isClanarina() {
    return clanarina;
  }

  public void setClanarina(boolean clanarina) {
    this.clanarina = clanarina;
  }

  public Integer getOrganizator_id() {
    return organizator_id;
  }

  public void setOrganizator_id(Integer organizator_id) {
    this.organizator_id = organizator_id;
  }
}