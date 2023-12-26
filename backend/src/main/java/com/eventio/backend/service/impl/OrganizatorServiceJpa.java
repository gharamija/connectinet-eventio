package com.eventio.backend.service.impl;

import com.eventio.backend.dao.OrganizatorRepository;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.dto.OrganizatorDTO;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.service.OrganizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizatorServiceJpa implements OrganizatorService {

    @Autowired
    private OrganizatorRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Optional<Organizator> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public boolean registerOrganizator(OrganizatorDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        Organizator organizator = new Organizator(new requestKorisnikDTO(dto), dto.getNazivOrganizacije(), dto.getAdresa(), dto.getPoveznica(), dto.getClanarina());
        organizator = repository.saveAndFlush(organizator);
        if (organizator.getId() == null) {
            return false;
        }
        return true;
    }

    @Override
    public Organizator Spremi(Organizator organizator){
        return repository.saveAndFlush(organizator);
    }
    @Override
    public boolean updateOrganizator(OrganizatorDTO dto, Integer id){
        Organizator user = repository.findById(id).get();
        if (!"".equals(dto.getPassword()))
            user.setPassword(encoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setNazivOrganizacije(dto.getNazivOrganizacije());
        user.setPoveznica(dto.getPoveznica());
        user.setPoveznica(dto.getPoveznica());
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
}
