package com.eventio.backend.dao;

import com.eventio.backend.domain.Organizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizatorRepository extends JpaRepository<Organizator, Integer> {
}
