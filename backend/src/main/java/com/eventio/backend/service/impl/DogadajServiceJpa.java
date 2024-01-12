package com.eventio.backend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Comparator;
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
import org.springframework.web.multipart.MultipartFile;

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
  @Override
  public List<Dogadaj> sortirajDogadaje(List<Dogadaj> filtriraniDogadaji, String sort) {
    Comparator<Dogadaj> comparator = switch (sort) {
      case "vrijeme-silazno" -> Comparator.comparing(Dogadaj::getVrijemePocetka).reversed();
      case "zainteresirani-uzlazno" -> Comparator.comparing(Dogadaj::zainteresiranost);
      case "zainteresirani-silazno" -> Comparator.comparing(Dogadaj::zainteresiranost).reversed();
      default -> Comparator.comparing(Dogadaj::getVrijemePocetka);
    };

    filtriraniDogadaji.sort(comparator);

    return filtriraniDogadaji;
  }
  @Override
  public List<Dogadaj> filtrirajDogadaje(List<Dogadaj> sviDogadaji, Kvartovi lokacija, String vrijeme, Vrste vrsta, String zavrseno, String placanje) {
    if (lokacija != null) {
      sviDogadaji = sviDogadaji.stream()
              .filter(dogadaj -> dogadaj.getLokacija().equals(lokacija))
              .collect(Collectors.toList());
    }

    if (vrijeme != null) {
      LocalDateTime trenutnoVrijeme = LocalDateTime.now();

      switch (vrijeme) {
        case "1":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(1))
                          && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(1)))
                  .collect(Collectors.toList());
          break;

        case "7":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(7))
                          && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(7)))
                  .collect(Collectors.toList());
          break;

        case "30":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(trenutnoVrijeme.minusDays(30))
                          && dogadaj.getVrijemePocetka().isBefore(trenutnoVrijeme.plusDays(30)))
                  .collect(Collectors.toList());
          break;
        default:
          break;
      }
    }

    if (vrsta != null) {
      sviDogadaji = sviDogadaji.stream()
              .filter(dogadaj -> dogadaj.getVrsta().equals(vrsta))
              .collect(Collectors.toList());
    }

    if (zavrseno != null) {
      switch (zavrseno) {
        case "Da":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> dogadaj.getVrijemePocetka().isBefore(LocalDateTime.now()))
                  .collect(Collectors.toList());
          break;
        case "Ne":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> dogadaj.getVrijemePocetka().isAfter(LocalDateTime.now()))
                  .collect(Collectors.toList());
          break;
        default:
          break;
      }
    }
    if (placanje != null) {
      switch (placanje) {
        case "placa se":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> Integer.parseInt(dogadaj.getCijenaUlaznice()) > 0 )
                  .collect(Collectors.toList());
          break;
        case "besplatno":
          sviDogadaji = sviDogadaji.stream()
                  .filter(dogadaj -> Integer.parseInt(dogadaj.getCijenaUlaznice()) == 0 )
                  .collect(Collectors.toList());
          break;
        default:
          break;
      }
    }
    return sviDogadaji;
  }
@Override
public void izbrisiDogadaj(Dogadaj dogadaj){
    dogadajRepository.delete(dogadaj);
}

  @Override
  public void saveFile(String uploadPath, String fileName, MultipartFile file) throws IOException {
    Path uploadDir = Path.of(uploadPath).toAbsolutePath().normalize();
    Path filePath = uploadDir.resolve(fileName);

    if (!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }

    try (var inputStream = file.getInputStream()) {
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
  }
}

