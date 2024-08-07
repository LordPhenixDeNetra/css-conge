export class SiteDTO {

  constructor(data:Partial<SiteDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  numSite?: number|null;
  nomSite?: string|null;

}
