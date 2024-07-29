package com.example.simulationdevelopmentepidemic.services;

import com.example.simulationdevelopmentepidemic.ResourceNotFoundException;
import com.example.simulationdevelopmentepidemic.models.Symulation;
import com.example.simulationdevelopmentepidemic.repositories.PopulationRepository;
import com.example.simulationdevelopmentepidemic.repositories.SymulationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymulationService {

    private final SymulationRepository symulationRepository;
    private final PopulationRepository populationRepository;
    private final PopulationService populationService;

    @Autowired
    public SymulationService(SymulationRepository symulationRepository, PopulationRepository populationRepository, PopulationService populationService) {
        this.symulationRepository = symulationRepository;
        this.populationRepository = populationRepository;
        this.populationService = populationService;
    }

    public List<Symulation> getSymulations() {
        return symulationRepository.findAll();
    }

    public Symulation saveSymulation(Symulation symulation) {
        Symulation savedSymulation = symulationRepository.save(symulation);
        populationService.createInitialPopulation(savedSymulation);
        return savedSymulation;
    }

    @Transactional
    public Symulation updateSymulation(int id, Symulation updatedSymulation) {
        if (symulationRepository.existsById(id)) {
            updatedSymulation.setId(id);
            Symulation savedSymulation = symulationRepository.save(updatedSymulation);
            System.out.println(savedSymulation);
            populationService.updatePopulationForSymulation(savedSymulation);
            return savedSymulation;
        } else {
            throw new ResourceNotFoundException("Symulacja o podanym id " + id + " nie istnieje");
        }
    }

    public void deleteSymulation(int id) {
        if (symulationRepository.existsById(id)) {
            symulationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Symulacja o podanym id " + id + " nie istnieje");
        }
    }
}

