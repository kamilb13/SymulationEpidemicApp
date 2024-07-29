package com.example.simulationdevelopmentepidemic.controllers;

import com.example.simulationdevelopmentepidemic.models.Symulation;
import com.example.simulationdevelopmentepidemic.services.SymulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SymulationController {

    private final SymulationService symulationService;

    @Autowired
    public SymulationController(SymulationService symulationService) {
        this.symulationService = symulationService;
    }

    @GetMapping("/getSymulations")
    public List<Symulation> symulation(){
        return symulationService.getSymulations();
    }

    @PostMapping("/saveSymulation")
    public Symulation symulation(@RequestBody Symulation symulation){
        return symulationService.saveSymulation(symulation);
    }

    @PutMapping("/updateSymulation/{id}")
    public Symulation updateSymulation(@PathVariable int id, @RequestBody Symulation symulation) {
        return symulationService.updateSymulation(id, symulation);
    }

    @DeleteMapping("/deleteSymulation/{id}")
    public void deleteSymulation(@PathVariable int id){
        symulationService.deleteSymulation(id);
    }
}
