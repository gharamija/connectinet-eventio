package com.eventio.backend.dao;

import com.eventio.backend.domain.Misc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiscRepository extends JpaRepository<Misc, String> {
}
