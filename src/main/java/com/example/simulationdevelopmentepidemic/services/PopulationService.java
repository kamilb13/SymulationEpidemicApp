package com.example.simulationdevelopmentepidemic.services;

import com.example.simulationdevelopmentepidemic.ResourceNotFoundException;
import com.example.simulationdevelopmentepidemic.models.Population;
import com.example.simulationdevelopmentepidemic.models.Symulation;
import com.example.simulationdevelopmentepidemic.repositories.PopulationRepository;
import com.example.simulationdevelopmentepidemic.repositories.SymulationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class PopulationService {

    private final PopulationRepository populationRepository;
    private final SymulationRepository symulationRepository;


    @Autowired
    public PopulationService(PopulationRepository populationRepository, SymulationRepository symulationRepository) {
        this.populationRepository = populationRepository;
        this.symulationRepository = symulationRepository;
    }

    public void createInitialPopulation(Symulation symulation) {
        int pi = symulation.getI();         // ilosc zakazonych ustalona na poziomie tworzenia symulacji
        int pv = symulation.getP() - pi;    // zdrowi, podatni moga byc wszyscy
        int pm = 0;                         // przyjmujemy 0
        int pr = 0;                         // przyjmujemy 0

        Population population = new Population(pi, pv, pm, pr, symulation.getId());
        populationRepository.save(population);

        iterationsForSymulation(symulation, population);
    }

    public void iterationsForSymulation(Symulation symulation, Population initialPopulation) {
        int pi = initialPopulation.getPi();     // zakażeni
        int pv = initialPopulation.getPv();     // zdrowi
        int pm = initialPopulation.getPm();     // zmarli
        int pr = initialPopulation.getPr();     // wyzdrowiali
        double r = symulation.getR();           // wskaźnik R (ile jedna osoba zaraża osób)
        double m = symulation.getM();           // śmiertelność
        int ti = symulation.getTi();            // czas do wyzdrowienia
        int tm = symulation.getTm();            // czas do śmierci
        int numberDays = symulation.getTs();    // liczba dni symulacji

        double beta = r / ti;                   // wskaźnik zakaźności (to nie to samo co R)

        int totalPopulation = pi + pv + pm + pr;

        Queue<Integer> infections = new LinkedList<>(); // kolejka zakazonych
        Queue<Integer> deaths = new LinkedList<>();     // kolejka zgonów

        for (int i = 0; i < numberDays; i++){

            // zarazeni
            int zarazeni = (int) ((r/100) * pv);
            pi = pi + zarazeni;
            pv = pv - zarazeni;

            // dodaje zarazonych do kolejki zarazen
            for (int j = 1; j < zarazeni; j++){
                infections.add(i);
                if (Math.random() < m / 100) {     // m / 100 zeby uzyskac % smiertelnosci
                    deaths.add(i);
                }
            }

            // usuwam wyzdrowialych z grupy zakazonych po "ti" dniach
            while (!infections.isEmpty() && i - infections.peek() >= ti) {
                infections.poll();
                pr++; // wyzdrowiali++
                pi--; // zakazeni--
            }

            System.out.println(infections);

            // zarazeni do grupa zmarlych
            while (!deaths.isEmpty() && i - deaths.peek() >= tm) {
                deaths.poll();
                pm++; // zmarli++
                pi--; // zakazeni--
            }

            // while(infections){
            //
            // }

            System.out.println("Dzień numer " + i);
            System.out.println("Liczba nowych zakażeń: " + zarazeni);
            System.out.println("pi = " + pi + " pv = " + pv + " pm = " + pm + " pr = " + pr);



            Population dailyPopulation = new Population(pi, pv, pm, pr, symulation.getId());
            populationRepository.save(dailyPopulation);
        }
        System.out.println(infections);
    }


    @Transactional
    public void updatePopulationForSymulation(Symulation symulation) {
        populationRepository.deleteBySymulationId(symulation.getId());
        System.out.println(symulation);
        System.out.println(symulation.getId());
        createInitialPopulation(symulation);
    }

    public List<Population> getPopulationData(int id) {
        return populationRepository.findBySymulationId(id);
    }
}
