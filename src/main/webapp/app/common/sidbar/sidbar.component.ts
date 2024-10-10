import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, Router} from "@angular/router";

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

  handleLogout() {

    if(this.router.url.includes("/salarierInfo/info/")){
      console.log("/salarierInfo/info/")

      localStorage.removeItem("demandeConge");
      localStorage.removeItem("firstReload");
      localStorage.removeItem("reloadable");
      localStorage.removeItem("salarier");
      this.router.navigate([``]);
    }

    if(this.router.url.includes("/users-admin")){
      console.log("/users-admin")

      sessionStorage.removeItem("admin");
      this.router.navigate([``]);
    }

    // localStorage.removeItem("demandeConge");
    // localStorage.removeItem("firstReload");
    // localStorage.removeItem("reloadable");
    // localStorage.removeItem("salarier");
    // this.router.navigate([``]);

  }
}
