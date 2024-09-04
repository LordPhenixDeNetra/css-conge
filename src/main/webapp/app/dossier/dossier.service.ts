import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from 'environments/environment';
import { DossierDTO } from 'app/dossier/dossier.model';
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root',
})
export class DossierService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/dossiers';

  getAllDossiers() {
    return this.http.get<DossierDTO[]>(this.resourcePath);
  }

  getDossier(id: number) {
    return this.http.get<DossierDTO>(this.resourcePath + '/' + id);
  }

  // createDossier(dossierDTO: DossierDTO) {
  //   return this.http.post<number>(this.resourcePath, dossierDTO);
  // }

  createDossier(dossier: DossierDTO, files: File[]): Observable<any> {
    const formData: FormData = new FormData();

    // Ajouter les champs du dossier au formData
    Object.keys(dossier).forEach(key => {
      const value = dossier[key as keyof DossierDTO];
      if (value !== null && value !== undefined) {
        formData.append(key, value.toString());
      }
    });

    // Ajouter les fichiers au formData avec la clé 'files'
    files.forEach(file => {
      formData.append('files', file, file.name);
    });

    const headers = new HttpHeaders({
      'enctype': 'multipart/form-data'
    });

    return this.http.post(this.resourcePath + '/upload/files', formData, { headers });
  }

  updateDossier(id: number, dossierDTO: DossierDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, dossierDTO);
  }

  deleteDossier(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
