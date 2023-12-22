package com.eventio.backend.service.impl;

import com.eventio.backend.dao.ZainteresiranostRepository;
import com.eventio.backend.domain.Zainteresiranost;
import com.eventio.backend.service.ZainteresiranostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZainteresiranostServiceJpa implements ZainteresiranostService {
    @Autowired
    private ZainteresiranostRepository repository;
    @Override
    public Zainteresiranost spremiZainteresiranost(Zainteresiranost zainteresiranost) {
        return repository.save(zainteresiranost);
    }
}
