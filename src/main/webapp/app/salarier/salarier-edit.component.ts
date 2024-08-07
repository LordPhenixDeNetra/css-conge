import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SalarierService } from 'app/salarier/salarier.service';
import { SalarierDTO } from 'app/salarier/salarier.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-salarier-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './salarier-edit.component.html'
})
export class SalarierEditComponent implements OnInit {

  salarierService = inject(SalarierService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  siteValues?: Map<number,string>;
  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
    numArticleL143: new FormControl(null, [Validators.required, Validators.maxLength(20)]),
    nin: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    prenom: new FormControl(null, [Validators.maxLength(100)]),
    nom: new FormControl(null, [Validators.maxLength(25)]),
    dateNaissane: new FormControl(null),
    lieuNaissance: new FormControl(null, [Validators.maxLength(255)]),
    nomMereComplet: new FormControl(null, [Validators.maxLength(255)]),
    prenomPere: new FormControl(null, [Validators.maxLength(25)]),
    adresse: new FormControl(null, [Validators.maxLength(255)]),
    email: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    telephone1: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    telephone2: new FormControl(null, [Validators.maxLength(100)]),
    compteBancaire: new FormControl(null, [Validators.maxLength(255)]),
    dateEmbauche: new FormControl(null, [Validators.required]),
    salaire: new FormControl(null, [Validators.required]),
    debutConge: new FormControl(null, [Validators.required]),
    site: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@salarier.update.success:Salarier was updated successfully.`,
      SALARIER_NIN_UNIQUE: $localize`:@@Exists.salarier.nin:This Nin is already taken.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
    this.salarierService.getSiteValues()
        .subscribe({
          next: (data) => this.siteValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.salarierService.getSalarier(this.currentId!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new SalarierDTO(this.editForm.value);
    this.salarierService.updateSalarier(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/salariers'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
