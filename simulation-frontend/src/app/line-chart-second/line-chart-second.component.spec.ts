import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LineChartSecondComponent } from './line-chart-second.component';

describe('LineChartSecondComponent', () => {
  let component: LineChartSecondComponent;
  let fixture: ComponentFixture<LineChartSecondComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LineChartSecondComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LineChartSecondComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
