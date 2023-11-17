package com.eventio.backend.domain;

import jakarta.persistence.*;

@Entity
public class Notifikacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notifikacija_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "posjetitelj_id", nullable = false)
    private Korisnik posjetitelj;

    private String vrsta;
    private String lokacija;
}
