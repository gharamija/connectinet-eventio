package com.eventio.backend.dao;

import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Vrste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DogadajRepository extends JpaRepository<Dogadaj, Integer> {

  Optional<List<Dogadaj>> findByOrganizator(Organizator organizator);

  Optional<List<Dogadaj>> findByVrsta(Vrste vrsta);

  Optional<List<Dogadaj>> findByLokacija(Kvartovi lokacija);

  Optional<List<Dogadaj>> findByVrijemePocetkaBetween(LocalDateTime start, LocalDateTime end);

  Optional<List<Dogadaj>> findByOrganizatorAndVrijemePocetkaBetween(Organizator organizator, LocalDateTime start, LocalDateTime end);
}