package com.eventio.backend.dao;

import com.eventio.backend.domain.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {
}
