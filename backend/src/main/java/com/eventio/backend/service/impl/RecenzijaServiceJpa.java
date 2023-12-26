package com.eventio.backend.service.impl;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Recenzija;
import com.eventio.backend.dto.RecenzijaDTO;
import com.eventio.backend.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import com.eventio.backend.dao.RecenzijaRepository;
import org.springframework.stereotype.Service;

@Service
public class RecenzijaServiceJpa implements RecenzijaService {
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Override
    public Recenzija spremiRecenziju(Korisnik korisnik, Dogadaj dogadaj, RecenzijaDTO dto) {
        return recenzijaRepository.save(new Recenzija(dto, korisnik,dogadaj));
    }
}
