import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalarierInfoComponent } from './salarier-info.component';

describe('SalarierInfoComponent', () => {
  let component: SalarierInfoComponent;
  let fixture: ComponentFixture<SalarierInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalarierInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SalarierInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
