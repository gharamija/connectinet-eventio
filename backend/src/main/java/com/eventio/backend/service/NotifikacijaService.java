package com.eventio.backend.service;

import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.domain.Vrste;
import org.aspectj.weaver.ast.Not;

import java.util.List;
import java.util.Optional;

public interface NotifikacijaService {
    Optional<Notifikacija> findById(Integer id);
    boolean deleteNotifiakcija(Notifikacija notifikacija);
    void spremi(Notifikacija notifikacija);
    Optional<List<Notifikacija>>  findByLokacijaAndAndVrsta(Kvartovi lokacija, Vrste vrsta);
    void posaljiNotifikacije(Kvartovi lokacija, Vrste vrsta, String ime);
}
