package com.eventio.backend.service;

import com.eventio.backend.domain.Organizator;
import com.eventio.backend.dto.OrganizatorDTO;

import java.util.Optional;

public interface OrganizatorService {
    Optional<Organizator> findById(Integer id);

    boolean registerOrganizator(OrganizatorDTO dto);
}
