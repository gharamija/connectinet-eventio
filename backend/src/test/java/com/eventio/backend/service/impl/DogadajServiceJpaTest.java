package com.eventio.backend.service.impl;

import com.eventio.backend.dao.DogadajRepository;
import com.eventio.backend.domain.Dogadaj;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DogadajServiceJpaTest {

  @InjectMocks
  private DogadajServiceJpa dogadajService;

  @Mock
  private DogadajRepository dogadajRepository;

  @Test
  public void testListAll() {
    List<Dogadaj> dummyDogadaji = Arrays.asList(new Dogadaj(), new Dogadaj());
    when(dogadajRepository.findAll()).thenReturn(dummyDogadaji);

    List<Dogadaj> result = dogadajService.listAll();

    assertEquals(2, result.size());
  }

  @Test
  public void testSortirajDogadaje() {
    Dogadaj dogadaj1 = new Dogadaj();
    dogadaj1.setVrijemePocetka(LocalDateTime.now().plusDays(2));

    Dogadaj dogadaj2 = new Dogadaj();
    dogadaj2.setVrijemePocetka(LocalDateTime.now().plusDays(1));

    Dogadaj dogadaj3 = new Dogadaj();
    dogadaj3.setVrijemePocetka(LocalDateTime.now().plusDays(3));

    List<Dogadaj> dummyDogadaji = Arrays.asList(dogadaj1, dogadaj2, dogadaj3);

    when(dogadajRepository.findAll()).thenReturn(dummyDogadaji);

    List<Dogadaj> result = dogadajService.sortirajDogadaje(dogadajService.listAll(), "vrijeme-silazno");

    assertEquals(dogadaj3, result.get(0));
    assertEquals(dogadaj1, result.get(1));
    assertEquals(dogadaj2, result.get(2));
  }
}
