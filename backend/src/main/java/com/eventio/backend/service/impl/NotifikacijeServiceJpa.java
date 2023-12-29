package com.eventio.backend.service.impl;

import com.eventio.backend.dao.NotifikacijaRepository;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.service.NotifikacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotifikacijeServiceJpa implements NotifikacijaService {
    @Autowired
    private NotifikacijaRepository notifikacijaRepository;

    @Override
    public Optional<Notifikacija> findById(Integer id){
        return notifikacijaRepository.findById(id);
    }
    @Override
    public boolean deleteNotifiakcija(Notifikacija notifikacija){
        notifikacijaRepository.delete(notifikacija);
        return true;
    }
    @Override
    public void spremi(Notifikacija notifikacija){
        notifikacijaRepository.save(notifikacija);
    }
}
