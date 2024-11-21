import {AfterViewInit, Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgForOf} from "@angular/common";
import {SalarierDTO} from "../../salarier/salarier.model";

interface MenuItem {
  icon: string;
  label: string;
  link: string;
  active: boolean;
}

@Component({
  selector: 'app-sidbar',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './sidbar.component.html',
  styleUrl: './sidbar.component.scss',
})
export class SidbarComponent implements OnInit{

  router = inject(Router);
  route = inject(ActivatedRoute);

  salarier : SalarierDTO = this.getConnectedSalarier();
  // salarierId = 0;
  menuItems: MenuItem[] = [];

  menuItemsForAdmin: MenuItem[] = [
    { icon: 'bi-bar-chart', label: 'Tableau de bord', link: '#', active: true },
    { icon: 'bi-person', label: 'DMT', link: '#', active: true },
    { icon: 'bi-person', label: 'Demandes Congé', link: 'demandeConge', active: true },
  ];

  menuItemsForSalarier: MenuItem[] = [
    { icon: 'bi-person', label: 'Accueil', link: `salarierInfo/info/${this.salarier.id}`, active: true },
    { icon: 'bi-person', label: 'Message', link: `salarierInfo/info/${this.salarier.id}/messages`, active: true },
  ];

  ngOnInit() {
    this.salarier = JSON.parse(localStorage.getItem("salarier")!)
    this.menuItems = this.getMenuItemList(this.router);
  }

  getConnectedSalarier(){
    return JSON.parse(localStorage.getItem("salarier")!)
  }


  handleLogout() {
    if (this.router.url.includes("/salarierInfo/info/")) {
      console.log("/salarierInfo/info/");

      localStorage.removeItem("demandeConge");
      localStorage.removeItem("firstReload");
      localStorage.removeItem("reloadable");
      localStorage.removeItem("salarier");
      this.router.navigate([``]);
    }

    if (this.router.url.includes("/users-admin")) {
      console.log("/users-admin");

      sessionStorage.removeItem("admin");
      this.router.navigate([``]);
    }
  }

  getMenuItemList(router: Router): MenuItem[] {
    if (router.url.includes("salarierInfo/info")) {
      return this.menuItemsForSalarier;
    } else {
      return this.menuItemsForAdmin;
    }
  }
}
