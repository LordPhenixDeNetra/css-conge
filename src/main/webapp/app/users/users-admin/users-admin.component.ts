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
  ],
  templateUrl: './users-admin.component.html',
  styleUrl: './users-admin.component.scss'
})
export class UsersAdminComponent implements AfterViewInit, OnInit{

  fileUrl: string | null = null;

  private _liveAnnouncer = inject(LiveAnnouncer);
  private dmtService = inject(DmtService);

  // displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  // displayedColumns: string[] = [];
  displayedColumns: string[] = DmtDTO.getAttributeNames();

  // dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  dataSource! : MatTableDataSource<DmtDTO, MatPaginator>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  ngOnInit() {
    this.loadData();
    if (!this.displayedColumns.includes('Fichier')) {
      this.displayedColumns.push('Fichier');
    }
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

  // loadData() {
  //   this.dmtService.getAllDmts()
  //     .subscribe({
  //       next: (data) => {
  //         this.dataSource = new MatTableDataSource<DmtDTO>(data);
  //         this.dataSource.paginator = this.paginator;
  //         this.displayedColumns = DmtDTO.getAttributeNames();
  //         this.dataSource.sort = this.sort;
  //
  //         if (!this.displayedColumns.includes('actions')) {
  //           this.displayedColumns.push('actions');
  //         }
  //       },
  //       error: (error) => {
  //         console.log("Error");
  //       }
  //     });
  // }

  loadData() {
    this.dmtService.getAllDmts()
      .subscribe({
        next: (data) => {
          this.dataSource = new MatTableDataSource<DmtDTO>(data);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;

          if (!this.displayedColumns.includes('Fichier')) {
            this.displayedColumns.push('Fichier');
          }
        },
        error: (error) => {
          console.log("Error", error);
        }
      });
  }


  generateActionButtons(id: number): string {
    return `
    <button mat-icon-button (click)="downloadFile(${id})">
      <mat-icon>download</mat-icon>
    </button>
    <button mat-icon-button (click)="viewFile(${id})">
      <mat-icon>visibility</mat-icon>
    </button>
    <button mat-icon-button (click)="viewFileInIframe(${id})">
      <mat-icon>open_in_browser</mat-icon>
    </button>
  `;
  }



  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  handleDelete(element : any) {

  }

  editElement(element : any) {

  }

  deleteElement(element : any) {

  }

  protected readonly MatTooltip = MatTooltip;

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}

/*

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
  {position: 11, name: 'Sodium', weight: 22.9897, symbol: 'Na'},
  {position: 12, name: 'Magnesium', weight: 24.305, symbol: 'Mg'},
  {position: 13, name: 'Aluminum', weight: 26.9815, symbol: 'Al'},
  {position: 14, name: 'Silicon', weight: 28.0855, symbol: 'Si'},
  {position: 15, name: 'Phosphorus', weight: 30.9738, symbol: 'P'},
  {position: 16, name: 'Sulfur', weight: 32.065, symbol: 'S'},
  {position: 17, name: 'Chlorine', weight: 35.453, symbol: 'Cl'},
  {position: 18, name: 'Argon', weight: 39.948, symbol: 'Ar'},
  {position: 19, name: 'Potassium', weight: 39.0983, symbol: 'K'},
  {position: 20, name: 'Calcium', weight: 40.078, symbol: 'Ca'},
];

*/
