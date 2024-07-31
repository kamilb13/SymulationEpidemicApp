import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface Symulation {
  id: number;
  n: string;
  p: number;
  i: number;
  r: number;
  m: number;
  ti: number;
  tm: number;
  ts: number;
}

@Component({
  selector: 'app-simulation-list',
  templateUrl: './simulation-list.component.html',
  styleUrls: ['./simulation-list.component.css']
})
export class SimulationListComponent implements OnInit {
  symulations: Symulation[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.loadSymulations();
  }

  loadSymulations(): void {
    this.http.get<Symulation[]>('http://localhost:8080/getSymulations').subscribe(data => {
      this.symulations = data;
    });
  }

  editSymulation(id: number): void {
    this.router.navigate(['/edit-symulation', id]);
  }

  deleteSymulation(id: number): void {
    if (confirm('Are you sure you want to delete this simulation?')) {
      this.http.delete(`http://localhost:8080/deleteSymulation/${id}`).subscribe(() => {
        this.loadSymulations();
      });
    }
  }

  viewChart(id: number): void {
    this.router.navigate(['/chart', id]);
  }

  updateSymulation(updatedSymulation: Symulation): void {
    const index = this.symulations.findIndex(sym => sym.id === updatedSymulation.id);
    if (index !== -1) {
      this.symulations[index] = updatedSymulation;
    }
  }
}
