import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { SalarierService } from 'app/salarier/salarier.service';
import { SalarierDTO } from 'app/salarier/salarier.model';


@Component({
  selector: 'app-salarier-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './salarier-list.component.html'})
export class SalarierListComponent implements OnInit, OnDestroy {

  salarierService = inject(SalarierService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  salariers?: SalarierDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@salarier.delete.success:Salarier was removed successfully.`,
      'salarier.demandeConge.salarier.referenced': $localize`:@@salarier.demandeConge.salarier.referenced:This entity is still referenced by Demande Conge ${details?.id} via field Salarier.`
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
    this.salarierService.getAllSalariers()
        .subscribe({
          next: (data) => this.salariers = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.salarierService.deleteSalarier(id)
          .subscribe({
            next: () => this.router.navigate(['/salariers'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/salariers'], {
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
