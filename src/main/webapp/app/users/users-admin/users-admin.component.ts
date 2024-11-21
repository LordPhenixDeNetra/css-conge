import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {MatPaginator} from "@angular/material/paginator";
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, MatSortHeader, Sort} from "@angular/material/sort";
import {DmtService} from "../../dmt-add/dmt.service";
import {DmtDTO} from "../../dmt-add/dmt.model";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MatButton, MatIconAnchor, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {DisplayActionButtonsPipe} from "../../pipes/display-action-buttons.pipe";
import {MatTooltip} from "@angular/material/tooltip";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {SidbarComponent} from "../../common/sidbar/sidbar.component";
import {DialogComponent} from "../../common/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {SalarierDTO} from "../../salarier/salarier.model";
import {UserService} from "../user.service";
import {ToastrService} from "ngx-toastr";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";
import {DemandeCongeService} from "../../demande-conge/demande-conge.service";
import {DemandeCongeDTO} from "../../demande-conge/demande-conge.model";
import {DossierService} from "../../dossier/dossier.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-users-admin',
  standalone: true,
  imports: [
    MatTabGroup,
    MatTab,
    MatTable,
    MatPaginator,
    MatHeaderRow,
    MatRow,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRowDef,
    MatRowDef,
    MatSort,
    MatSortHeader,
    NgForOf,
    NgIf,
    RouterLink,
    MatIconAnchor,
    MatIcon,
    MatIconButton,
    DisplayActionButtonsPipe,
    MatButton,
    MatTooltip,
    MatFormField,
    MatInput,
    SidbarComponent,
  ],
  templateUrl: './users-admin.component.html',
  styleUrl: './users-admin.component.scss'
})
export class UsersAdminComponent implements AfterViewInit, OnInit {

  fileUrl: string | null = null;

  private _liveAnnouncer = inject(LiveAnnouncer);
  private dmtService = inject(DmtService);
  private dialog = inject(MatDialog);
  private userService = inject(UserService);
  private dossierService = inject(DossierService);
  private toast = inject(ToastrService);
  private demandeCongeService = inject(DemandeCongeService);


  lenDemande = 0

  displayedColumns: string[] = DmtDTO.getAttributeNames();
  displayedDemandeColumns: string[] = DemandeCongeDTO.getAttributeNames();
  dataSource!: MatTableDataSource<DmtDTO, MatPaginator>;
  demandeDataSource!: MatTableDataSource<DemandeCongeDTO, MatPaginator>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  ngOnInit() {
    this.loadData();
    this.loadDataDemandeConge();
  }

  downloadFile(id: number): void {
    this.dmtService.getFile(id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'file.pdf'; // Nom par défaut du fichier téléchargé
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    });
  }

  viewFile(id: number): void {
    this.dmtService.getFile(id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url); // Ouvre le fichier dans une nouvelle fenêtre/onglet
      window.URL.revokeObjectURL(url);
    });
  }

  openFilesDossierInNewTabs(id: number): void {
    let fileUrls: string[] = [];
    let baseUrl = environment.apiPath + '/api/dossiers/getfile/';

    this.dossierService.getDossier(id).subscribe({
      next: dossier => {
        // Ajoute les fichiers dans la liste
        fileUrls.push(baseUrl + dossier.attestationCessationPaie);
        fileUrls.push(baseUrl + dossier.attestationTravail);
        fileUrls.push(baseUrl + dossier.certificatMedical);
        fileUrls.push(baseUrl + dossier.dernierBulletinSalaire);
        fileUrls.push(baseUrl + dossier.copieCNI);

        console.log(fileUrls)

        // Utilise un délai pour ouvrir chaque fichier
        fileUrls.forEach((fileUrl, index) => {
          setTimeout(() => {
            window.open(fileUrl!, '_blank');
          }, index * 500); // Ajoute un délai de 500 ms entre chaque ouverture
        });
      },
      error: err => {
        console.error('Erreur lors de la récupération du dossier', err);
      }
    });
  }

  viewFileInIframe(id: number): void {
    this.dmtService.getFile(id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      this.fileUrl = url;
    });
  }

  ngAfterViewInit() {
    this.loadData();
    if (!this.displayedColumns.includes('Fichier')) {
      this.displayedColumns.push('Fichier');
    }
    // this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadDataDemandeConge(){
    this.demandeCongeService.getAllDemandeConges().subscribe(
      {
        next: (data) =>{
          this.demandeDataSource = new MatTableDataSource<DemandeCongeDTO>(data);
          this.demandeDataSource.paginator = this.paginator;
          this.demandeDataSource.sort = this.sort;
        },
        error: (error) => {
          console.log("Error", error);
        }
      }
    )
  }

  loadData() {
    this.dmtService.getAllDmts()
      .subscribe({
        next: (data) => {
          this.dataSource = new MatTableDataSource<DmtDTO>(data);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;

          this.lenDemande = data.length;

          if (!this.displayedColumns.includes('Fichier')) {
            this.displayedColumns.push('Fichier');
          }
        },
        error: (error) => {
          console.log("Error", error);
        }
      });
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  protected readonly MatTooltip = MatTooltip;

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  validateDMT(element : DmtDTO) {
    const dialogRef = this.dialog.open(DialogComponent,{
      data: {action: "validate"},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.userService.validateDMT(element).subscribe(
          value => {
            this.toast.success("DMT Validé avec succés\n" +
              "l'allocataire sera notifier", "Validation de la DMT",{
              // timeOut: 10000,
              // progressBar:true,

            });
            console.log(value)
            this.loadData();
          },
          error => {

          }

        );
      }
    });
  }

  invalidateDMT(id : number) {
    const dialogRef = this.dialog.open(DialogComponent,{
      data: {action: "invalidate"},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.dmtService.deleteDmt(id, 1).subscribe(
          value => {
            this.toast.success("DMT invalidé avec succés\n" +
              "l'allocataire sera notifier", "Invalidation de la DMT",{
              // timeOut: 10000,
              // progressBar:true,

            });
            console.log(value)
            this.loadData();
          },
          error => {

          }
        );
      }
    });
  }


}
