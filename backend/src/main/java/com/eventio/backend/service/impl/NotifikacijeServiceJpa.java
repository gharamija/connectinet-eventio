package com.eventio.backend.service.impl;

import com.eventio.backend.dao.NotifikacijaRepository;
import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Notifikacija;
import com.eventio.backend.domain.Vrste;
import com.eventio.backend.service.EmailSenderServise;
import com.eventio.backend.service.NotifikacijaService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotifikacijeServiceJpa implements NotifikacijaService {
    @Autowired
    private NotifikacijaRepository notifikacijaRepository;
    @Autowired
    private EmailSenderServise emailSenderServise;

    @Override
    public Optional<Notifikacija> findById(Integer id) {
        return notifikacijaRepository.findById(id);
    }

    @Override
    public boolean deleteNotifiakcija(Notifikacija notifikacija) {
        notifikacijaRepository.delete(notifikacija);
        return true;
    }

    @Override
    public void spremi(Notifikacija notifikacija) {
        notifikacijaRepository.save(notifikacija);
    }

    @Override
    public Optional<List<Notifikacija>> findByLokacijaAndAndVrsta(Kvartovi lokacija, Vrste vrsta) {
        return notifikacijaRepository.findByLokacijaAndAndVrsta(lokacija, vrsta);
    }

    @Override
    public void posaljiNotifikacije(Kvartovi lokacija, Vrste vrsta, String ime) {
        List<Notifikacija> notifikacijaList = findByLokacijaAndAndVrsta(lokacija, vrsta).get();
        Set<String> uniqueEmailSet = new HashSet<>();
        for (Notifikacija notifikacija : notifikacijaList) {
            Korisnik posjetitelj = notifikacija.getPosjetitelj();
            if (posjetitelj != null) {
                String email = posjetitelj.getEmail();
                if (email != null && !email.isEmpty()) {
                    uniqueEmailSet.add(email);
                }
            }
        }
        String poruka = "Dodan je dogadaj naziva: " + ime + " na lokaciju " + lokacija.name() + " vrste " + vrsta.name()
                + ".";
        for (String uniqueEmail : uniqueEmailSet)
            emailSenderServise.sendSimpleEmail(uniqueEmail, "OBAVIJEST", poruka);
    }

}
