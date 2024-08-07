import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SalarierDTO } from 'app/salarier/salarier.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class SalarierService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/salariers';

  getAllSalariers() {
    return this.http.get<SalarierDTO[]>(this.resourcePath);
  }

  getSalarier(id: number) {
    return this.http.get<SalarierDTO>(this.resourcePath + '/' + id);
  }

  getSalarierByNin(nin: string) {
    return this.http.get<SalarierDTO>(this.resourcePath + '/getByNin/' + nin);
  }

  createSalarier(salarierDTO: SalarierDTO) {
    return this.http.post<number>(this.resourcePath, salarierDTO);
  }

  updateSalarier(id: number, salarierDTO: SalarierDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, salarierDTO);
  }

  deleteSalarier(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getSiteValues() {
    return this.http.get<Record<string,number>>(this.resourcePath + '/siteValues')
        .pipe(map(transformRecordToMap));
  }

  // getSiteValues() {
  //   return this.http.get<{ id: number, name: string }[]>(this.resourcePath + '/siteValues');
  // }

}
