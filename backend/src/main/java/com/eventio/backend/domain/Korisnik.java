package com.eventio.backend.domain;

import com.eventio.backend.dto.requestKorisnikDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik implements UserDetails {
    @Serial
    private static final long serialVersionUID = 923686612483130334L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnikId")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated
    @Column(nullable = false)
    private Uloga uloga;

    @JsonIgnore
    @OneToMany(mappedBy = "posjetitelj", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzija> recenzije;
    @JsonIgnore
    @OneToMany(mappedBy = "posjetitelj", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zainteresiranost> zainteresiranosti;
    @JsonIgnore
    @OneToMany(mappedBy = "posjetitelj", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notifikacija> notifikacije;

    public Korisnik() {
    }

    public Korisnik(requestKorisnikDTO dto) {
        this.recenzije = new ArrayList<>();
        this.zainteresiranosti = new ArrayList<>();
        this.notifikacije = new ArrayList<>();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.uloga = dto.getUloga();
    }
    public Integer getId() {
        return id;
    }

    @Override
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

    @Override
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

    public List<Recenzija> getRecenzije() {
        return recenzije;
    }

    public List<Zainteresiranost> getZainteresiranosti() {
        return zainteresiranosti;
    }

    public List<Notifikacija> getNotifikacije() {
        return notifikacije;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getUloga()));
    }
}
