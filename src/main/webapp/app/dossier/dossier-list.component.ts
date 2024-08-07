import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { DossierService } from 'app/dossier/dossier.service';
import { DossierDTO } from 'app/dossier/dossier.model';


@Component({
  selector: 'app-dossier-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dossier-list.component.html'})
export class DossierListComponent implements OnInit, OnDestroy {

  dossierService = inject(DossierService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  dossiers?: DossierDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@dossier.delete.success:Dossier was removed successfully.`,
      'dossier.demandeConge.dossier.referenced': $localize`:@@dossier.demandeConge.dossier.referenced:This entity is still referenced by Demande Conge ${details?.id} via field Dossier.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.dossierService.getAllDossiers()
        .subscribe({
          next: (data) => this.dossiers = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.dossierService.deleteDossier(id)
          .subscribe({
            next: () => this.router.navigate(['/dossiers'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/dossiers'], {
                  state: {
                    msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                  }
                });
                return;
              }
              this.errorHandler.handleServerError(error.error)
            }
          });
    }
  }

}
