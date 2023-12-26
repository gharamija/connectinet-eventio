package com.eventio.backend.service;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.dto.requestKorisnikDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface KorisnikService extends UserDetailsService {
    List<Korisnik> listAll();
    Optional<Korisnik> findById(Integer id);
    Optional<Korisnik> findByUsername(String username);
    Optional<Korisnik> findByEmail(String email);
    boolean registerUser(requestKorisnikDTO dto);
    boolean updateUser(requestKorisnikDTO dto, Integer id);
}
