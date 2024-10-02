import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputRowComponent} from "../common/input-row/input-row.component";
import {Router, RouterLink} from "@angular/router";
import {SalarierService} from "../salarier/salarier.service";
import {ErrorHandler} from "../common/error-handler.injectable";
import {SalarierDTO} from "../salarier/salarier.model";
import {DmtService} from "./dmt.service";
import {DmtDTO} from "./dmt.model";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-dmt-add',
  standalone: true,
  imports: [
    FormsModule,
    InputRowComponent,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './dmt-add.component.html',
  styleUrl: './dmt-add.component.scss'
})
export class DmtAddComponent {

  dmtService = inject(DmtService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);
  toater = inject(ToastrService);

  siteValues?: Map<number,string>;
  // siteValues?: { id: number, name: string }[];

  files: { [key: string]: File } = {};

  selectedFile: File | null = null;

  addForm = new FormGroup({
    numArticleL143: new FormControl(null, [Validators.required, Validators.maxLength(20)]),
    nin: new FormControl(null, [Validators.required, Validators.maxLength(25)]),
    prenom: new FormControl(null, [Validators.maxLength(100)]),
    nom: new FormControl(null, [Validators.maxLength(25)]),
    dateNaissane: new FormControl(null),
    lieuNaissance: new FormControl(null, [Validators.maxLength(255)]),
    adresse: new FormControl(null, [Validators.maxLength(255)]),
    email: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    telephone1: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    document: new FormControl(null, [Validators.required])


  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@salarier.create.success:Salarier was created successfully.`,
      SALARIER_NIN_UNIQUE: $localize`:@@Exists.salarier.nin:This Nin is already taken.`
    };
    return messages[key];
  }

  ngOnInit() {
    // this.dmtService.getSiteValues()
    //   .subscribe({
    //     next: (data) => this.siteValues = data,
    //     error: (error) => this.errorHandler.handleServerError(error.error)
    //   });
  }

  // Gérer la sélection du fichier
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid || !this.selectedFile) {
      return;
    }
    const data = new DmtDTO(this.addForm.value);
    this.dmtService.createDmtWithFile(data, this.selectedFile)
      .subscribe({
        next: () => {
          this.toater.success(
            this.addForm.value.prenom + " " + this.addForm.value.nom + "Votre DMT est ajouté evec succes evenez dans 2 jours pour faire votre demande",
            "Ajout DMT"
          )
          // this.router.navigate(['/salariers'], {
          //   // state: {
          //   //   msgSuccess: this.getMessage('created')
          //   // }
          // })
        },
        error: (error) => {
          this.toater.error(
            "Reverifier les informations entrer",
            "Ajout DMT"
          )
          // this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        }
      });
  }


  handleFileInput(event: Event, field: string) {

    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.files[field] = input.files[0];
    }
  }
}
