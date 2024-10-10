import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { salaierAuthGuard } from './salaier-auth.guard';

describe('salaierAuthGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => salaierAuthGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
