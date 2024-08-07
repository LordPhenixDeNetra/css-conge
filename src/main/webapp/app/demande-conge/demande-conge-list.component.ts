import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { DemandeCongeService } from 'app/demande-conge/demande-conge.service';
import { DemandeCongeDTO } from 'app/demande-conge/demande-conge.model';


@Component({
  selector: 'app-demande-conge-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './demande-conge-list.component.html'})
export class DemandeCongeListComponent implements OnInit, OnDestroy {

  demandeCongeService = inject(DemandeCongeService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  demandeConges?: DemandeCongeDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@demandeConge.delete.success:Demande Conge was removed successfully.`    };
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
    this.demandeCongeService.getAllDemandeConges()
        .subscribe({
          next: (data) => this.demandeConges = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.demandeCongeService.deleteDemandeConge(id)
          .subscribe({
            next: () => this.router.navigate(['/demandeConges'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
