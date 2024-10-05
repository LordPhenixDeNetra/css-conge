import {Component, inject, OnInit} from '@angular/core';

import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {NgIf, TitleCasePipe} from "@angular/common";
export interface DialogData {
  action: "modify"| "delete" | "validate" | "logout" | "invalidate";
}

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    TitleCasePipe,
    NgIf
  ],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.scss'
})
export class DialogComponent implements OnInit{

  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly action = this.data.action;
  actionTitle!: string;
  actionVerb!: string;
  constructor(public dialogRef: MatDialogRef<DialogComponent>) {}

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  ngOnInit(): void {
    switch (this.action){
      case "validate": this.actionVerb="confirmer"; this.actionTitle="la validation";break;
      case "invalidate": this.actionVerb="confirmer"; this.actionTitle="la non validation";break;
      case "logout": this.actionVerb="confirmer"; this.actionTitle="déconnexion";break;
      case "modify": this.actionVerb="modifier"; this.actionTitle="modification";break;
      default: this.actionVerb= "supprimer";this.actionTitle = "suppression";break;
    }
  }

}
