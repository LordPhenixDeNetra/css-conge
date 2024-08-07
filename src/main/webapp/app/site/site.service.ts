import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SiteDTO } from 'app/site/site.model';


@Injectable({
  providedIn: 'root',
})
export class SiteService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/sites';

  getAllSites() {
    return this.http.get<SiteDTO[]>(this.resourcePath);
  }

  getSite(id: number) {
    return this.http.get<SiteDTO>(this.resourcePath + '/' + id);
  }

  createSite(siteDTO: SiteDTO) {
    return this.http.post<number>(this.resourcePath, siteDTO);
  }

  updateSite(id: number, siteDTO: SiteDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, siteDTO);
  }

  deleteSite(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
