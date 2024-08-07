import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {InputRowComponent} from "../../common/input-row/input-row.component";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {DemandeCongeService} from "../../demande-conge/demande-conge.service";
import {DemandeCongeDTO} from "../../demande-conge/demande-conge.model";
import {parseJson} from "@angular/cli/src/utilities/json-file";
import {ReloadService} from "../../services/reload.service";

@Component({
  selector: 'app-salarier-info',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    InputRowComponent,
    NgOptimizedImage,
    NgIf
  ],
  templateUrl: './salarier-info.component.html',
  styleUrl: './salarier-info.component.scss'
})
export class SalarierInfoComponent implements OnInit{

  salarier: any; // Variable pour stocker les données du salarié
  salarierDemande : any | null; // Variable pour stocker les information sur la demande
  demandeId : number | null;
  reloadable! : string | null;

  constructor(private router: Router,
              private demandeCongeService : DemandeCongeService,
              ) {

    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { demandeId: number };
    this.demandeId = state?.demandeId;

    console.log("Demande ID=================================");
    console.log("DEMANDE ID : ", this.demandeId);
    console.log("Demande ID========================");
  }

  ngOnInit(): void {
    let salarierData = localStorage.getItem('salarier');
    let demandeData : string = String(localStorage.getItem('demandeConge'));
    this.salarierDemande = JSON.parse(demandeData);

    this.reloadable = localStorage.getItem("reloadable")!;

    console.log("Demande ID=================================");
    console.log("DEMANDE ID : ", this.demandeId);
    console.log("Demande ID========================");

    if (salarierData) {
      this.salarier = JSON.parse(salarierData);
      if (demandeData == null){
        this.demandeCongeService.findBySalarierId(this.salarier.id).subscribe(
          response => {
            this.salarierDemande = response;
            // this.salarierDemande = new DemandeCongeDTO(response);
            localStorage.removeItem('demandeConge');
            localStorage.setItem('demandeConge', JSON.stringify(response));
            console.log(this.salarierDemande);
            console.log("Demande ID=================================");
            console.log("DEMANDE ID : ", this.demandeId);
            console.log("Demande ID========================");
          }
        )
      }
      console.log(this.salarier);
    } else {
      console.error('Aucune donnée de salarié disponible');
    }
  }

  setNullReloadable(){
    this.reloadable = null;
  }

  handleReload() {
    localStorage.removeItem('reloadable');
    // localStorage.removeItem('firstReload');
    this.setNullReloadable();
    location.reload();
  }
}
