package com.eventio.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eventio.backend.dao.DogadajRepository;
import com.eventio.backend.domain.Dogadaj;
import com.eventio.backend.domain.Kvartovi;
import com.eventio.backend.domain.Organizator;
import com.eventio.backend.domain.Vrste;
import com.eventio.backend.dto.requestDogadajDTO;
import com.eventio.backend.dto.responseDogadajDTO;
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
  public List<responseDogadajDTO> pretvori_DTO(List<Dogadaj> Dogadaji) {
    List<responseDogadajDTO> dogadajDTOList = Dogadaji.stream()
            .map(this::mapirajUDogadajDTO)
            .collect(Collectors.toList());

    return dogadajDTOList;
  }

  private responseDogadajDTO mapirajUDogadajDTO(Dogadaj dogadaj) {
    return new responseDogadajDTO(dogadaj);
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
  public boolean updateDogadaj(requestDogadajDTO dto, Integer id){
    Optional<Dogadaj> optionalDogadaj = dogadajRepository.findById(id);

    if (optionalDogadaj.isPresent()) {
      Dogadaj dogadaj = optionalDogadaj.get();

      dogadaj.setOpis(dto.getOpis());
      dogadaj.setGalerija(dto.getGalerija());
      dogadaj.setCijenaUlaznice(dto.getCijenaUlaznice());
      dogadaj.setVrsta(dto.getVrsta());
      dogadaj.setNazivDogadaja(dto.getNazivDogadaja());
      dogadaj.setLokacija(dto.getLokacija());
      dogadaj.setOpisLokacije(dto.getOpisLokacije());
      dogadaj.setVrijemePocetka(dto.getVrijemePocetka());

      dogadajRepository.saveAndFlush(dogadaj);
      return true;
    } else {
      return false;
    }
  }
}
