package com.eventio.backend.dao;

import com.eventio.backend.domain.Zainteresiranost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZainteresiranostRepository extends JpaRepository<Zainteresiranost, Integer> {
}
