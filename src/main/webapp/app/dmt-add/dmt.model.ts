export class DmtDTO {

  constructor(data:Partial<DmtDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  numArticleL143?: string|null;
  nin?: string|null;
  prenom?: string|null;
  nom?: string|null;
  dateNaissane?: string|null;
  lieuNaissance?: string|null;
  adresse?: string|null;
  email?: string|null;
  telephone1?: string|null;
  document?: string|null;

  static getAttributeNames(): string[] {
    return [
      // 'id',
      'numArticleL143',
      'nin',
      'prenom',
      'nom',
      'dateNaissane',
      'lieuNaissance',
      'adresse',
      'email',
      'telephone1',
      // 'document'
    ]
  }

}
