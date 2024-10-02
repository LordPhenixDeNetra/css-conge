import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidbar',
  standalone: true,
  imports: [],
  templateUrl: './sidbar.component.html',
  styleUrl: './sidbar.component.scss',
})
export class SidbarComponent implements OnInit{

  router = inject(Router);

  ngOnInit() {
  }

  handleLogoutSalarier() {
    localStorage.removeItem("demandeConge");
    localStorage.removeItem("firstReload");
    localStorage.removeItem("reloadable");
    localStorage.removeItem("salarier");
    this.router.navigate([``]);

  }
}
