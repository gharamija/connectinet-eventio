package com.eventio.backend.service;

import com.eventio.backend.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DogadajService {
  List<Dogadaj> listAll();
  Optional<Dogadaj> findById(Integer id);
  Optional<List<Dogadaj>> findByOrganizator(Organizator organizator);
  Optional<List<Dogadaj>> findByVrsta(Vrste vrsta);
  Optional<List<Dogadaj>> findByLokacija(Kvartovi lokacija);
  Optional<List<Dogadaj>> findByVrijemePocetkaBetween(LocalDateTime start, LocalDateTime end);
  public Optional<List<Dogadaj>> findByOrganizatorAndVrijemePocetkaBetween(Organizator organizator, LocalDateTime start, LocalDateTime end);
  public Dogadaj spremiDogadaj(Dogadaj dogadaj);
}
