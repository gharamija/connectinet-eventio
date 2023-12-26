package com.eventio.backend.service.impl;

import com.eventio.backend.dao.ZainteresiranostRepository;
import com.eventio.backend.domain.Zainteresiranost;
import com.eventio.backend.service.ZainteresiranostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eventio.backend.domain.Kategorija;
import com.eventio.backend.domain.Zainteresiranost;
import com.eventio.backend.domain.Korisnik;

import java.util.List;

@Service
public class ZainteresiranostServiceJpa implements ZainteresiranostService {

  @Autowired
  private ZainteresiranostRepository zainteresiranostRepository;

  @Override
  public List<Zainteresiranost> findByPosjetitelj(Korisnik posjetitelj) {
    return zainteresiranostRepository.findByPosjetitelj(posjetitelj);
  }

  @Override
  public List<Zainteresiranost> findByPosjetiteljAndKategorijaIn(Korisnik posjetitelj, List<Kategorija> kategorije) {
    return zainteresiranostRepository.findByPosjetiteljAndKategorijaIn(posjetitelj, kategorije);
  }
   @Override
   public Zainteresiranost spremiZainteresiranost(Zainteresiranost zainteresiranost) {
        return zainteresiranostRepository.save(zainteresiranost);
  }
  // Implementirajte ostale metode prema potrebama
}
