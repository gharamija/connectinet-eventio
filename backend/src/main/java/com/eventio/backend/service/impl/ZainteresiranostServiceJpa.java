package com.eventio.backend.service.impl;

import com.eventio.backend.dao.ZainteresiranostRepository;
import com.eventio.backend.domain.*;
import com.eventio.backend.service.ZainteresiranostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eventio.backend.domain.Zainteresiranost;

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
    public Zainteresiranost findByPosjetiteljAndDogadaj(Korisnik posjetitelj, Dogadaj dogadaj) {
        if (posjetitelj == null || dogadaj == null)
            return null;
        return zainteresiranostRepository.findByPosjetiteljAndDogadaj(posjetitelj, dogadaj);
    }

   @Override
   public Zainteresiranost spremiZainteresiranost(Korisnik posjetitelj, Dogadaj dogadaj, Kategorija kategorija) {
      Zainteresiranost trazena = findByPosjetiteljAndDogadaj(posjetitelj,dogadaj);
      System.out.println(trazena);
       if (trazena == null)
            return zainteresiranostRepository.save(new Zainteresiranost(posjetitelj,dogadaj,kategorija));
       trazena.setKategorija(kategorija);
       return zainteresiranostRepository.save(trazena);
  }
}
