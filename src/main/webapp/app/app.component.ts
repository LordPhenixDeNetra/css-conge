import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, NavigationStart, Router, RouterLink, RouterOutlet} from '@angular/router';
import { HeaderComponent } from 'app/common/header/header.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute)

  msgSuccess = null;
  msgInfo = null;
  msgError = null;

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        const navigationState = this.router.getCurrentNavigation()?.extras.state;
        this.msgSuccess = navigationState?.['msgSuccess'] || null;
        this.msgInfo = navigationState?.['msgInfo'] || null;
        this.msgError = navigationState?.['msgError'] || null;
      }
    });

    this.shouldShowNavbar();
  }

  shouldShowNavbar(): boolean {
    const routeData = this.activatedRoute.firstChild?.snapshot.data['showNavbar'];
    return routeData !== false; // Afficher la navbar si showNavbar n'est pas explicitement false
  }

}
