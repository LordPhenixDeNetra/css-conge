import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DossierService } from 'app/dossier/dossier.service';
import { DossierDTO } from 'app/dossier/dossier.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import {DemandeCongeService} from "../demande-conge/demande-conge.service";
import {DemandeCongeDTO} from "../demande-conge/demande-conge.model";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-dossier-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './dossier-add.component.html',
  styleUrl: './dossier-add.component.scss'
})
export class DossierAddComponent implements OnInit{

  salarierId: number | null = 0;
  dossierId: number | null = 0;
  dossierService = inject(DossierService);
  demandeCongeService = inject(DemandeCongeService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);
  route = inject(ActivatedRoute);
  toater = inject(ToastrService);

  addForm = new FormGroup({
    attestationTravail: new FormControl(null, [Validators.required]),
    attestationCessationPaie: new FormControl(null, [Validators.required]),
    certificatMedical: new FormControl(null, [Validators.required]),
    dernierBulletinSalaire: new FormControl(null, [Validators.required]),
    copieCNI: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  files: { [key: string]: File } = {};

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.salarierId = +params.get('id')!;
      console.log(this.salarierId); // Vous pouvez utiliser l'ID ici
    });
  }

  handleFileInput(event: Event, field: string) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.files[field] = input.files[0];
    }
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }

    const data = new DossierDTO(this.addForm.value);

    const fileArray = Object.values(this.files);

    this.dossierService.createDossier(data, fileArray).subscribe(

      response => {
        console.log('Données envoyées avec succès', response);
        // alert('Dossier créé avec succès!');

        this.dossierId = response.id;

        if(this.dossierId != null && this.dossierId != 0){

          this.demandeCongeService.createWithSalarierAndDossier(new DemandeCongeDTO(), Number(this.salarierId), Number(this.dossierId)).subscribe(
            response =>{
              localStorage.removeItem('demandeConge');
              localStorage.setItem('demandeConge', JSON.stringify(response));
            }
          );
        }
        this.router.navigate([`/salarierInfo/info/${this.salarierId}`]);
      },
      error => {
        this.toater.error(
          "Verifier bien vos information",
          "Creation de Dossier"
        )
        console.error('Erreur lors de l\'envoi des données', error);
        // alert('Erreur lors de la création du dossier.');
      }

    );

  }

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@dossier.create.success:Dossier was created successfully.`
    };
    return messages[key];
  }
}
