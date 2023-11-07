package com.eventio.backend.dao;

import com.eventio.backend.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByUsername(String username);
    Optional<Korisnik> findByEmail(String email);
}
