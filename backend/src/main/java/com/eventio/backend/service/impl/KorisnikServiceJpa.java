package com.eventio.backend.service.impl;

import com.eventio.backend.dao.KorisnikRepository;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Override
    public boolean registerUser(requestKorisnikDTO dto) {
        try {
            dto.setPassword(encoder.encode(dto.getPassword()));
            Korisnik user = new Korisnik(dto);
            repository.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(requestKorisnikDTO dto, Integer id){
        Korisnik user = repository.findById(id).get();
        if (!"".equals(dto.getPassword()))
            user.setPassword(encoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        try {
            repository.saveAndFlush(user);
            //radi promjene AuthenticationPrincipal
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Korisnik) {
                Korisnik authenticatedUser = (Korisnik) authentication.getPrincipal();
                authenticatedUser.setUsername(user.getUsername());
                authenticatedUser.setEmail(user.getEmail());
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    @Override
    public void deleteUser(Korisnik korisnik){
        repository.delete(korisnik);
    }

}
