package com.example.simulationdevelopmentepidemic.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Symulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String n;
    int p;
    int i;
    double r;
    double m;
    int ti;
    int tm;
    int ts;

}
