package com.eventio.backend.service.impl;

import com.eventio.backend.dao.ZainteresiranostRepository;
import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kategorija;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Zainteresiranost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ZainteresiranostServiceJpaTest {

  @Mock
  private ZainteresiranostRepository zainteresiranostRepository;

  @InjectMocks
  private ZainteresiranostServiceJpa zainteresiranostService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSpremiZainteresiranost_Nova() {
    Korisnik posjetitelj = new Korisnik();
    Dogadaj dogadaj = new Dogadaj();
    Kategorija kategorija = Kategorija.SIGURNO;

    when(zainteresiranostRepository.findByPosjetiteljAndDogadaj(posjetitelj, dogadaj)).thenReturn(null);
    when(zainteresiranostRepository.save(any(Zainteresiranost.class)))
        .thenReturn(new Zainteresiranost(posjetitelj,dogadaj, kategorija));

    Zainteresiranost savedZainteresiranost = zainteresiranostService.spremiZainteresiranost(posjetitelj, dogadaj, kategorija);

    assertEquals(posjetitelj, savedZainteresiranost.getPosjetitelj());
    assertEquals(dogadaj, savedZainteresiranost.getDogadaj());
    assertEquals(kategorija, savedZainteresiranost.getKategorija());

    verify(zainteresiranostRepository).findByPosjetiteljAndDogadaj(posjetitelj, dogadaj);
    verify(zainteresiranostRepository).save(any(Zainteresiranost.class));
  }

  @Test
  void testSpremiZainteresiranost_Postojeća() {
    Korisnik posjetitelj = new Korisnik();
    Dogadaj dogadaj = new Dogadaj();
    Kategorija novaKategorija = Kategorija.SIGURNO;
    Zainteresiranost postojećaZainteresiranost = new Zainteresiranost(posjetitelj, dogadaj, Kategorija.MOZDA);

    when(zainteresiranostRepository.findByPosjetiteljAndDogadaj(posjetitelj, dogadaj))
        .thenReturn(postojećaZainteresiranost);
    when(zainteresiranostRepository.save(any(Zainteresiranost.class)))
        .thenReturn(new Zainteresiranost(posjetitelj,dogadaj,novaKategorija));

    Zainteresiranost savedZainteresiranost = zainteresiranostService.spremiZainteresiranost(posjetitelj, dogadaj, novaKategorija);

    assertEquals(posjetitelj, savedZainteresiranost.getPosjetitelj());
    assertEquals(dogadaj, savedZainteresiranost.getDogadaj());
    assertEquals(novaKategorija, savedZainteresiranost.getKategorija());

    verify(zainteresiranostRepository).findByPosjetiteljAndDogadaj(posjetitelj, dogadaj);
    verify(zainteresiranostRepository).save(any(Zainteresiranost.class));
  }
}