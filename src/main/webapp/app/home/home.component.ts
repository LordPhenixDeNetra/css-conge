import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { environment } from 'environments/environment';
import {Router, RouterLink} from '@angular/router';
import {ErrorHandler} from "../common/error-handler.injectable";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SalarierDTO} from "../salarier/salarier.model";
import {SalarierService} from "../salarier/salarier.service";
import {DemandeCongeService} from "../demande-conge/demande-conge.service";
import {DemandeCongeDTO} from "../demande-conge/demande-conge.model";


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink, NgOptimizedImage, FormsModule, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  environment = environment;

  demandeJsonData!: DemandeCongeDTO;

  errorHandler = inject(ErrorHandler);
  demandeCongeService = inject(DemandeCongeService);

  demandeJsonDataId! : number;

  constructor(private salarierService: SalarierService,
              private router : Router,
              private formBuilder : FormBuilder) {
  }

  ngOnInit() {
    let firstReload = localStorage.getItem('firstReload');

    if (firstReload){

    }else {
      localStorage.setItem('firstReload', 'yes');
      localStorage.setItem('reloadable', 'yes');
    }

  }


  getUserForm = this.formBuilder.group({
    nin: this.formBuilder.control("", Validators.compose([Validators.required])),
  });

  handleSubmit() {
    if (this.getUserForm.valid) {
      let ninSalarier = this.getUserForm.value.nin;
      console.log(ninSalarier)
      this.salarierService.getSalarierByNin(String(ninSalarier)).subscribe(
        (response) => {
          let salarierId = response.id;
          localStorage.setItem('salarier', JSON.stringify(response));
          this.demandeCongeService.findBySalarierId(Number(salarierId)).subscribe(
            demande =>{
              // this.salarierDemande = new DemandeCongeDTO(response);
              // localStorage.setItem('reloadable', 'yes');
              localStorage.setItem('demandeConge', JSON.stringify(demande));
              this.demandeJsonData = demande;

              console.log("DEMANDE JSON DATA", this.demandeJsonData.id)

              this.demandeJsonDataId =  this.demandeJsonData.id!;
              this.router.navigate([`/salarierInfo/info/${salarierId}`], { state: { demandeId: this.demandeJsonDataId}});

            }
          )
          // this.router.navigate([`/salarierInfo/info/${salarierId}`], { state: { demandeId: salarierId}});
          // this.router.navigate([`/salarierInfo/info/${salarierId}`], { state: { demandeId: this.demandeJsonDataId}});
          // this.router.navigate([`/salarierInfo/info/${salarierId}`], { state: { demandeId: 10001}});

        },
        (error) => {
          console.error("Erreur lors de la recherche de l'utilisateur:", error);
        }
      );
    }
  }
}
