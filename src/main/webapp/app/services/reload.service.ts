import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReloadService {

  constructor() { }

  private reloadSubject = new BehaviorSubject<boolean>(true);
  reload$ = this.reloadSubject.asObservable();

  triggerReload() {
    this.reloadSubject.next(true);
  }

  resetReload() {
    this.reloadSubject.next(false);
  }

}
