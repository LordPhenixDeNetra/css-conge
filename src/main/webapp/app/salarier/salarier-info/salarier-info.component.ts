import {AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, signal} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {InputRowComponent} from "../../common/input-row/input-row.component";
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {DemandeCongeService} from "../../demande-conge/demande-conge.service";
import {DemandeCongeDTO} from "../../demande-conge/demande-conge.model";
import {parseJson} from "@angular/cli/src/utilities/json-file";
import {ReloadService} from "../../services/reload.service";
import {SalarierService} from "../salarier.service";
import {NMessageService} from "../../nmessage/nmessage.service";
import {NMessageDTO} from "../../nmessage/nmessage.model";
import {
  MatAccordion, MatExpansionModule,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatChip, MatChipSet, MatChipsModule} from "@angular/material/chips";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatButton} from "@angular/material/button";
import {SidbarComponent} from "../../common/sidbar/sidbar.component";

@Component({
  selector: 'app-salarier-info',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    InputRowComponent,
    NgOptimizedImage,
    NgIf,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelTitle,
    MatExpansionPanelDescription,
    MatExpansionModule,
    NgForOf,
    MatCardContent,
    MatCardHeader,
    MatCardFooter,
    MatChipSet,
    MatCard,
    MatChip,

    MatCardModule, MatChipsModule, MatProgressBarModule, MatButton, SidbarComponent, NgClass
  ],
  templateUrl: './salarier-info.component.html',
  styleUrl: './salarier-info.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,


})
export class SalarierInfoComponent implements OnInit, AfterViewInit{

  salarier: any; // Variable pour stocker les données du salarié
  messages!:NMessageDTO[];
  salarierDemande : any | null; // Variable pour stocker les information sur la demande
  demandeId : number | null;
  reloadable! : string | null;

  // readonly panelOpenState = signal(false)

  viewInit : boolean = false;

  panels = [
    { name: 'Panel 1' },
    { name: 'Panel 2' },
    { name: 'Panel 3' },
    { name: 'Panel 4' }
  ];


  // Sample data array (you can populate this dynamically from an API or service)
  actions = [
    { id: '#12345', utilisateur: 'Jean Dupont', action: 'Nouvelle demande', date: '01/10/2024', statut: 'Approuvé', badge: 'bg-success' },
    { id: '#12346', utilisateur: 'Marie Martin', action: 'Mise à jour profil', date: '01/10/2024', statut: 'En cours', badge: 'bg-info' },
    { id: '#12347', utilisateur: 'Paul Dubois', action: 'Changement allocation', date: '30/09/2024', statut: 'En attente', badge: 'bg-warning' }
  ];

  longText = `The Chihuahua is a Mexican breed of toy dog. It is named for the
  Mexican state of Chihuahua and is among the smallest of all dog breeds. It is
  usually kept as a companion animal or for showing.`;

  // panels = [];

  panelList : NMessageDTO[] = [];

  dogs = [
    { title: 'Poodle', subtitle: 'Non-sporting group' },
    { title: 'Poodle', subtitle: 'Non-sporting group' },
    { title: 'Poodle', subtitle: 'Non-sporting group' }
  ];

  panelOpenState: boolean[] = [];

  // panels: NMessageDTO[] = [];
  // panelOpenState: boolean[] = [];

  constructor(private router: Router,
              private demandeCongeService : DemandeCongeService,
              private salarierMessage : NMessageService,
              ) {

    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { demandeId: number };
    this.demandeId = state?.demandeId;

    console.log("Demande ID=================================");
    console.log("DEMANDE ID : ", this.demandeId);
    console.log("Demande ID========================");
  }

  ngAfterViewInit() {

    // this.salarierMessage.findAllMessageBySalarierId(this.salarier.id).subscribe({
    //   next: (data) => {
    //     this.messages = data;
    //     this.panelList = data;
    //     console.log(data)
    //   }
    // });
  }

  ngOnInit(): void {



    this.viewInit = true;
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

            this.salarierMessage.findAllMessageBySalarierId(this.salarier.id).subscribe({
              next: (data) => {
                this.messages = data;
                this.panelList = data;
                console.log("Message",data)
              }
              // error: (error) => this.errorHandler.handleServerError(error.error)
            });
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
