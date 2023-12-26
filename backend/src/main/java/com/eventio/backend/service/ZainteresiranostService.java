package com.eventio.backend.service;

import com.eventio.backend.domain.Kategorija;
import com.eventio.backend.domain.Zainteresiranost;
import com.eventio.backend.domain.Korisnik;


import java.util.List;

public interface ZainteresiranostService {
  public Zainteresiranost spremiZainteresiranost(Zainteresiranost zainteresiranost);
  List<Zainteresiranost> findByPosjetitelj(Korisnik posjetitelj);
  List<Zainteresiranost> findByPosjetiteljAndKategorijaIn(Korisnik posjetitelj, List<Kategorija> kategorije);
  // Dodajte ostale metode prema potrebama
}

