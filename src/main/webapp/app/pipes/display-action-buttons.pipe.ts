import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'displayActionButtons',
  standalone: true
})
export class DisplayActionButtonsPipe implements PipeTransform {

  transform(items: any[]): any[] {
    return items.map(item => ({
      ...item,
      actions: `
        <button mat-icon-button (click)="downloadFile(${item.id})">
          <mat-icon>download</mat-icon>
        </button>
        <button mat-icon-button (click)="viewFile(${item.id})">
          <mat-icon>visibility</mat-icon>
        </button>
        <button mat-icon-button (click)="viewFileInIframe(${item.id})">
          <mat-icon>open_in_browser</mat-icon>
        </button>
      `
    }));
  }

}
