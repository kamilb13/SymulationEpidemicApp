package com.example.simulationdevelopmentepidemic.repositories;

import com.example.simulationdevelopmentepidemic.models.Population;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Integer> {
    List<Population> findBySymulationId(int symulationId);
    void deleteBySymulationId(int symulationId);
}
