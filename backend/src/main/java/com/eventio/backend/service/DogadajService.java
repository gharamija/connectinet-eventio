package com.eventio.backend.service;

import com.eventio.backend.domain.*;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DogadajService {
  List<Dogadaj> listAll();
  List<responseDogadajDTO> pretvori_DTO(List<Dogadaj> Dogadaji);
  Optional<Dogadaj> findById(Integer id);
  Optional<List<Dogadaj>> findByOrganizator(Organizator organizator);
  Optional<List<Dogadaj>> findByVrsta(Vrste vrsta);
  Optional<List<Dogadaj>> findByLokacija(Kvartovi lokacija);
  Optional<List<Dogadaj>> findByVrijemePocetkaBetween(LocalDateTime start, LocalDateTime end);
  public Optional<List<Dogadaj>> findByOrganizatorAndVrijemePocetkaBetween(Organizator organizator, LocalDateTime start, LocalDateTime end);
  public Dogadaj spremiDogadaj(Dogadaj dogadaj);
  public boolean updateDogadaj(requestDogadajDTO dto, Integer id);
}
