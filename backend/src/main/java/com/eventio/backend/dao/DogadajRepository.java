package com.eventio.backend.dao;

import com.eventio.backend.domain.Dogadaj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogadajRepository extends JpaRepository<Dogadaj, Integer> {
}
