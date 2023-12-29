package com.eventio.backend.service;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Recenzija;
import com.eventio.backend.dto.RecenzijaDTO;

public interface RecenzijaService {
    Recenzija spremiRecenziju(Korisnik korisnik, Dogadaj dogadaj, RecenzijaDTO dto);
}
