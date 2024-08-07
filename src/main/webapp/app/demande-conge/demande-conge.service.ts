import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { DemandeCongeDTO } from 'app/demande-conge/demande-conge.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class DemandeCongeService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/demandeConges';

  getAllDemandeConges() {
    return this.http.get<DemandeCongeDTO[]>(this.resourcePath);
  }

  getDemandeConge(id: number) {
    return this.http.get<DemandeCongeDTO>(this.resourcePath + '/' + id);
  }

  createDemandeConge(demandeCongeDTO: DemandeCongeDTO) {
    return this.http.post<number>(this.resourcePath, demandeCongeDTO);
  }

  createWithSalarierAndDossier(demandeCongeDTO : DemandeCongeDTO, salarierId : number, dossierId : number){
    return this.http.post<number>(this.resourcePath + '/' + salarierId + '/' + dossierId, demandeCongeDTO);
  }

  findBySalarierId(id: number){
    return this.http.get<DemandeCongeDTO>(this.resourcePath + '/salarier/' + id);
  }

  updateDemandeConge(id: number, demandeCongeDTO: DemandeCongeDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, demandeCongeDTO);
  }

  deleteDemandeConge(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getSalarierValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/salarierValues')
        .pipe(map(transformRecordToMap));
  }

  getDossierValues() {
    return this.http.get<Record<string,number>>(this.resourcePath + '/dossierValues')
        .pipe(map(transformRecordToMap));
  }

}
