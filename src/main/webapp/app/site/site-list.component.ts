import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { SiteService } from 'app/site/site.service';
import { SiteDTO } from 'app/site/site.model';


@Component({
  selector: 'app-site-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './site-list.component.html'})
export class SiteListComponent implements OnInit, OnDestroy {

  siteService = inject(SiteService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  sites?: SiteDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@site.delete.success:Site was removed successfully.`,
      'site.salarier.site.referenced': $localize`:@@site.salarier.site.referenced:This entity is still referenced by Salarier ${details?.id} via field Site.`
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
    this.siteService.getAllSites()
        .subscribe({
          next: (data) => this.sites = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.siteService.deleteSite(id)
          .subscribe({
            next: () => this.router.navigate(['/sites'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/sites'], {
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
