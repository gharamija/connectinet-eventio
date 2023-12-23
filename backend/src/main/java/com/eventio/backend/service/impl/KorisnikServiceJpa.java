package com.eventio.backend.service.impl;

import com.eventio.backend.dao.KorisnikRepository;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServiceJpa implements KorisnikService {

    @Autowired
    private KorisnikRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<Korisnik> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Korisnik> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Korisnik> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Korisnik> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Korisnik> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }

    public boolean registerUser(requestKorisnikDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        Korisnik user = new Korisnik(dto);
        user = repository.saveAndFlush(user);
        if (user.getId() == null) {
            return false;
        }
        return true;
    }
}
