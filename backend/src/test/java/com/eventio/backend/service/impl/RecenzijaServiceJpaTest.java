package com.eventio.backend.service.impl;

import com.eventio.backend.dao.RecenzijaRepository;
import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Recenzija;
import com.eventio.backend.dto.RecenzijaDTO;
import com.eventio.backend.service.RecenzijaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class RecenzijaServiceJpaTest {

  @Mock
  private RecenzijaRepository recenzijaRepository;

  @InjectMocks
  private RecenzijaServiceJpa recenzijaService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSpremiRecenziju() {
    Korisnik korisnik = new Korisnik();
    Dogadaj dogadaj = new Dogadaj();
    RecenzijaDTO recenzijaDTO = new RecenzijaDTO();
    recenzijaDTO.setOcjena(5);
    recenzijaDTO.setTekst("Ovo je sjajan događaj!");

    when(recenzijaRepository.save(any(Recenzija.class))).thenReturn(new Recenzija(recenzijaDTO,korisnik,dogadaj));

    Recenzija savedRecenzija = recenzijaService.spremiRecenziju(korisnik, dogadaj, recenzijaDTO);

    assertEquals(korisnik, savedRecenzija.getPosjetitelj());
    assertEquals(dogadaj, savedRecenzija.getDogadaj());
    assertEquals(recenzijaDTO.getOcjena(), savedRecenzija.getOcjena());
    assertEquals(recenzijaDTO.getTekst(), savedRecenzija.getTekst());

    verify(recenzijaRepository).save(any(Recenzija.class));
  }
}