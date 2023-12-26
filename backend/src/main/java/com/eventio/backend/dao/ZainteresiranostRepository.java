package com.eventio.backend.dao;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kategorija;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Zainteresiranost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZainteresiranostRepository extends JpaRepository<Zainteresiranost, Integer> {
  List<Zainteresiranost> findByPosjetiteljAndKategorijaIn(Korisnik posjetitelj, List<Kategorija> kategorije);

  List<Zainteresiranost> findByPosjetitelj(Korisnik posjetitelj);

  Zainteresiranost findByPosjetiteljAndDogadaj(Korisnik posjetitelj, Dogadaj dogadaj);
}
