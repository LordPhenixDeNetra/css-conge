import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NMessageComponent } from './nmessage.component';

describe('NMessageComponent', () => {
  let component: NMessageComponent;
  let fixture: ComponentFixture<NMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
