import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { isSalarierGuardGuard } from './is-salarier-guard.guard';

describe('isSalarierGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => isSalarierGuardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
