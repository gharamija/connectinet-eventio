package com.eventio.backend.dao;

import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.domain.Vrste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Integer> {
    Optional<List<Notifikacija>> findByLokacijaAndAndVrsta(Kvartovi lokacija, Vrste vrsta);
}
