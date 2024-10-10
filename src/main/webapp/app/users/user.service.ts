import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SalarierDTO } from 'app/salarier/salarier.model';
import {map, Observable} from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';
import {NUserDTO} from "./user.model";
import {DmtDTO} from "../dmt-add/dmt.model";
import { ActivatedRoute } from '@angular/router';


@Injectable({
  providedIn: 'root',
})
export class UserService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/user';

  constructor(private route: ActivatedRoute) {
  }

  // Méthode pour effectuer le login
  loginAdmin(email: string, password: string): Observable<NUserDTO> {
    const loginRequest = { email, password };
    return this.http.post<NUserDTO>(this.resourcePath + '/login', loginRequest);
  }

  validateDMT(dmtDTO : DmtDTO) {
    return this.http.post<number>(this.resourcePath + '/validateDMT', dmtDTO)
  }

  isAdminLoggedIn() {
    return sessionStorage.getItem("admin") != null;
  }

  // loginAdmin(email: string, password: string) {
  //   return this.http.get<NUserDTO>(this.resourcePath + '/' + email + '/' + password);
  // }

  // getSiteValues() {
  //   return this.http.get<{ id: number, name: string }[]>(this.resourcePath + '/siteValues');
  // }

}
