import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { DemandeCongeService } from 'app/demande-conge/demande-conge.service';
import { DemandeCongeDTO } from 'app/demande-conge/demande-conge.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-demande-conge-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './demande-conge-add.component.html'
})
export class DemandeCongeAddComponent implements OnInit {

  demandeCongeService = inject(DemandeCongeService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  salarierValues?: Map<number,string>;
  dossierValues?: Map<number,string>;

  addForm = new FormGroup({
    status: new FormControl(null, [Validators.required]),
    salarier: new FormControl(null),
    dossier: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@demandeConge.create.success:Demande Conge was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.demandeCongeService.getSalarierValues()
        .subscribe({
          next: (data) => this.salarierValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.demandeCongeService.getDossierValues()
        .subscribe({
          next: (data) => this.dossierValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new DemandeCongeDTO(this.addForm.value);
    this.demandeCongeService.createDemandeConge(data)
        .subscribe({
          next: () => this.router.navigate(['/demandeConges'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
