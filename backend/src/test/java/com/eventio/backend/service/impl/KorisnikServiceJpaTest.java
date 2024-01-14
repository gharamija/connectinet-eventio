package com.eventio.backend.service.impl;

import com.eventio.backend.domain.Korisnik;
import com.eventio.backend.dto.requestKorisnikDTO;
import com.eventio.backend.dao.KorisnikRepository;
import com.eventio.backend.service.impl.KorisnikServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class KorisnikServiceJpaTest {

  @Mock
  private KorisnikRepository repository;

  @Mock
  private PasswordEncoder encoder;

  @InjectMocks
  private KorisnikServiceJpa korisnikService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRegisterUser_Success() {
    requestKorisnikDTO mockDTO = new requestKorisnikDTO();
    mockDTO.setUsername("user");
    mockDTO.setPassword("password");
    mockDTO.setEmail("test@example.com");

    when(encoder.encode(mockDTO.getPassword())).thenReturn("encodedPassword");
    when(repository.saveAndFlush(any(Korisnik.class))).thenReturn(new Korisnik(mockDTO));

    boolean result = korisnikService.registerUser(mockDTO);

    assertTrue(result);
  }

  @Test
  void testRegisterUser_Failure() {
    requestKorisnikDTO mockDTO = new requestKorisnikDTO();
    mockDTO.setUsername("user");
    mockDTO.setPassword("password");
    mockDTO.setEmail("test@example.com");

    when(encoder.encode(mockDTO.getPassword())).thenReturn("encodedPassword");
    doThrow(new RuntimeException("Simulated failure")).when(repository).saveAndFlush(any(Korisnik.class));

    boolean result = korisnikService.registerUser(mockDTO);

    assertFalse(result);
  }
}