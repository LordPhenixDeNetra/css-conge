export class DemandeCongeDTO {

  constructor(data?:Partial<DemandeCongeDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  status?: string|null;
  salarier?: number|null;
  dossier?: number|null;

}
