import { Injectable, inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from 'environments/environment';
import {map, Observable} from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';
import {DmtDTO} from "./dmt.model";


@Injectable({
  providedIn: 'root',
})
export class DmtService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/dmts';

  getAllDmts() {
    return this.http.get<DmtDTO[]>(this.resourcePath);
  }

  getDmt(id: number) {
    return this.http.get<DmtDTO>(this.resourcePath + '/' + id);
  }



  // createDmt(DmtDTO: DmtDTO) {
  //   return this.http.post<number>(this.resourcePath, DmtDTO);
  // }

  createDmtWithFile(dmtDTO: DmtDTO, file: File) {

    const formData: FormData = new FormData();

    // Ajouter les données du DTO au formData
    formData.append('numArticleL143', dmtDTO.numArticleL143!);
    formData.append('nin', dmtDTO.nin!);
    formData.append('prenom', dmtDTO.prenom!);
    formData.append('nom', dmtDTO.nom!);
    formData.append('dateNaissane', dmtDTO.dateNaissane!.toString());
    formData.append('lieuNaissance', dmtDTO.lieuNaissance!);
    formData.append('adresse', dmtDTO.adresse!);
    formData.append('email', dmtDTO.email!);
    formData.append('telephone1', dmtDTO.telephone1!);
    formData.append('document', dmtDTO.document!);

    // Ajouter le fichier au formData
    formData.append('file', file, file.name);

    const headers = new HttpHeaders({
      'enctype': 'multipart/form-data'
    });

    // Envoyer le formData comme le corps de la requête
    return this.http.post(this.resourcePath + '/file', formData, { headers });
  }

  getFile(id: number):Observable<Blob>{
    return this.http.get(`${this.resourcePath}/${id}/file`, { responseType: 'blob' });
  }

  // getFile(id: number){
  //   return this.http.get(`${this.resourcePath}/${id}/file`);
  // }


  updateDmt(id: number, dmtDTO: DmtDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, dmtDTO);
  }

  deleteDmt(id: number, sendMessage : number) {
    return this.http.delete(this.resourcePath + '/' + id + '/send/' + sendMessage);
  }

  // getSiteValues() {
  //   return this.http.get<Record<string,number>>(this.resourcePath + '/siteValues')
  //       .pipe(map(transformRecordToMap));
  // }

  // getSiteValues() {
  //   return this.http.get<{ id: number, name: string }[]>(this.resourcePath + '/siteValues');
  // }

}
