package com.eventio.backend.service;

import com.eventio.backend.domain.Notifikacija;
import org.aspectj.weaver.ast.Not;

import java.util.Optional;

public interface NotifikacijaService {
    Optional<Notifikacija> findById(Integer id);
    boolean deleteNotifiakcija(Notifikacija notifikacija);
    void spremi(Notifikacija notifikacija);
}
