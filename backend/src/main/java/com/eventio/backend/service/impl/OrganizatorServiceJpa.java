package com.eventio.backend.service.impl;

import com.eventio.backend.dao.OrganizatorRepository;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.dto.OrganizatorDTO;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.service.OrganizatorService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
