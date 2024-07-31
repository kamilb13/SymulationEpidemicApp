import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SimulationFormComponent } from './simulation-form/simulation-form.component';
import { SimulationListComponent } from "./simulation-list/simulation-list.component";
import {LineChartSecondComponent} from "./line-chart-second/line-chart-second.component";

const routes: Routes = [
  { path: '', component: SimulationListComponent },
  { path: 'symulations', component: SimulationListComponent },
  { path: 'add-simulation', component: SimulationFormComponent },
  { path: 'edit-symulation/:id', component: SimulationFormComponent },
  { path: 'chart/:id', component: LineChartSecondComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
