package com.eventio.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.eventio.backend.dao.DogadajRepository;
import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Vrste;
import com.eventio.backend.service.DogadajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogadajServiceJpa implements DogadajService {

  @Autowired
  private DogadajRepository dogadajRepository;

  public DogadajServiceJpa(DogadajRepository dogadajRepository) {
    this.dogadajRepository = dogadajRepository;
  }

  @Override
  public List<Dogadaj> listAll() {
    return dogadajRepository.findAll();
  }

  @Override
  public Optional<Dogadaj> findById(Integer id) {
    return dogadajRepository.findById(id);
  }

  @Override
  public Optional<List<Dogadaj>> findByOrganizator(Organizator organizator) {
    return dogadajRepository.findByOrganizator(organizator);
  }

  @Override
  public Optional<List<Dogadaj>> findByVrsta(Vrste vrsta) {
    return dogadajRepository.findByVrsta(vrsta);
  }

  @Override
  public Optional<List<Dogadaj>> findByLokacija(Kvartovi lokacija) {
    return dogadajRepository.findByLokacija(lokacija);
  }

  @Override
  public Optional<List<Dogadaj>> findByVrijemePocetkaBetween(LocalDateTime start, LocalDateTime end) {
    return dogadajRepository.findByVrijemePocetkaBetween(start, end);
  }

  @Override
  public Optional<List<Dogadaj>> findByOrganizatorAndVrijemePocetkaBetween(Organizator organizator, LocalDateTime start, LocalDateTime end) {
    return dogadajRepository.findByOrganizatorAndVrijemePocetkaBetween(organizator, start, end);
  }
  @Override
  public Dogadaj spremiDogadaj(Dogadaj dogadaj){
    return dogadajRepository.save(dogadaj);
  }
}
