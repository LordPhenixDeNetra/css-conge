import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import {NMessageDTO} from "../nmessage/nmessage.model";
import {SalarierDTO} from "../salarier/salarier.model";


@Injectable({
  providedIn: 'root',
})

export class NMessageService{
  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/messages';

  findAllMessageBySalarierId(salarierId: number) {
    return this.http.get<NMessageDTO[]>(this.resourcePath + '/' + salarierId);
  }
}
