package com.example.simulationdevelopmentepidemic.controllers;

import com.example.simulationdevelopmentepidemic.models.Population;
import com.example.simulationdevelopmentepidemic.models.Symulation;
import com.example.simulationdevelopmentepidemic.services.PopulationService;
import com.example.simulationdevelopmentepidemic.services.SymulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class PopulationController {
    private final PopulationService populationService;

    @Autowired
    public PopulationController(PopulationService populationService) {
        this.populationService = populationService;
    }
    @GetMapping("/getPopulationData/{id}")
    public List<Population> getPopulationData(@PathVariable int id) {
        return populationService.getPopulationData(id);
    }
}
