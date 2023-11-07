package com.eventio.backend.dao;

import com.eventio.backend.domain.Notifikacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Integer> {
}
