package com.eventio.backend.service.impl;

import com.eventio.backend.dao.MiscRepository;
import com.eventio.backend.domain.Misc;
import com.eventio.backend.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MiscServiseJpa implements MiscService {
@Autowired
    private MiscRepository repository;
    @Override
    public String dohvatiVrijednost(String ime){
        Optional<Misc> OptMisc = repository.findById(ime);
        if (OptMisc.isPresent()){
            return OptMisc.get().getVrijednost();
        }
        return "";
    }
    @Override
    public void postaviVrijednost(String ime, String vrijednost){
        Optional<Misc> OptMisc = repository.findById(ime);
        Misc misc;
        if (OptMisc.isPresent()){
            misc = OptMisc.get();
            misc.setVrijednost(vrijednost);
        }else {
            misc = new Misc(ime,vrijednost);
        }
        repository.save(misc);
    }
}
