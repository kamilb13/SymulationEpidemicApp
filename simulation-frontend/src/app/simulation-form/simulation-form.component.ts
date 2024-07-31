import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface Symulation {
  id?: number;
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
  selector: 'app-simulation-form',
  templateUrl: './simulation-form.component.html',
  styleUrls: ['./simulation-form.component.css']
})
export class SimulationFormComponent implements OnInit {
  symulation: Symulation = {
    n: '',
    p: 0,
    i: 0,
    r: 0,
    m: 0,
    ti: 0,
    tm: 0,
    ts: 0
  };
  isEditMode: boolean = false;
  id?: number;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isEditMode = true;
      this.http.get<Symulation>(`http://localhost:8080/getSymulation/${this.id}`).subscribe(data => {
        this.symulation = data;
      });
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.id) {
      this.http.put(`http://localhost:8080/updateSymulation/${this.id}`, this.symulation).subscribe(() => {
        this.router.navigate(['/symulations']);
      });
    } else {
      this.http.post('http://localhost:8080/saveSymulation', this.symulation).subscribe(() => {
        this.router.navigate(['/symulations']);
      });
    }
  }
}
