import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

Chart.register(...registerables);

@Component({
  selector: 'app-line-chart-second',
  templateUrl: './line-chart-second.component.html',
  styleUrls: ['./line-chart-second.component.css']
})
export class LineChartSecondComponent implements OnInit, OnChanges {
  @Input() symulationId!: number;
  public chart: any;
  public chartData: any[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.symulationId = +this.route.snapshot.paramMap.get('id')!;
    this.loadChartData();
    console.log("Initialized with symulationId: ", this.symulationId);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['symulationId'] && this.symulationId) {
      console.log('Detected changes in symulationId:', this.symulationId);
      this.loadChartData();
    }
  }

  loadChartData(): void {
    console.log("Fetching data for id: ", this.symulationId);
    this.http.get<any[]>(`http://localhost:8080/getPopulationData/${this.symulationId}`).subscribe(
      data => {
        console.log("Received data: ", data);
        this.chartData = data;
        this.renderChart();
      },
      error => {
        console.error("Error fetching data: ", error);
      }
    );
  }

  renderChart(): void {
    if (this.chart) {
      this.chart.destroy();
    }

    const numDays = this.chartData.length;
    const labels = Array.from({ length: numDays }, (_, i) => `Day ${i + 1}`);
    const infectedData = this.chartData.map(d => d.pi);
    const healthyData = this.chartData.map(d => d.pv);
    const recoveredData = this.chartData.map(d => d.pr);
    const deceasedData = this.chartData.map(d => d.pm);

    this.chart = new Chart('lineChart', {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Infected',
            data: infectedData,
            borderColor: 'red',
            fill: false
          },
          {
            label: 'Healthy',
            data: healthyData,
            borderColor: 'green',
            fill: false
          },
          {
            label: 'Recovered',
            data: recoveredData,
            borderColor: 'blue',
            fill: false
          },
          {
            label: 'Deceased',
            data: deceasedData,
            borderColor: 'black',
            fill: false
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: function(tooltipItem: any) {
                return `${tooltipItem.dataset.label}: ${tooltipItem.raw}`;
              }
            }
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Count'
            },
            ticks: {
              stepSize: 100 // interwal osi Y
            }
          },
          x: {
            title: {
              display: true,
              text: 'Days'
            }
          }
        }
      }
    });
  }
}
