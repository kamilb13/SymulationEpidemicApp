package com.example.simulationdevelopmentepidemic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Population {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int pi;     // iloscZakazonych
    int pv;     // zdrowiPodatni
    int pm;     // zmarli
    int pr;     // uzdrowieni

    int symulationId;


    public Population(int pi, int pv, int pm, int pr, int symulationId) {
        this.pi = pi;
        this.pv = pv;
        this.pm = pm;
        this.pr = pr;
        this.symulationId = symulationId;
    }
}
