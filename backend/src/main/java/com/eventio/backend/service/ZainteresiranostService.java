package com.eventio.backend.service;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kategorija;
import com.eventio.backend.domain.Zainteresiranost;
import com.eventio.backend.domain.Korisnik;


import java.util.List;

public interface ZainteresiranostService {
  Zainteresiranost spremiZainteresiranost(Korisnik posjetitelj, Dogadaj dogadaj, Kategorija kategorija);
  List<Zainteresiranost> findByPosjetitelj(Korisnik posjetitelj);
  List<Zainteresiranost> findByPosjetiteljAndKategorijaIn(Korisnik posjetitelj, List<Kategorija> kategorije);
}

