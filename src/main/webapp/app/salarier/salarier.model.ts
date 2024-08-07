export class SalarierDTO {

  constructor(data:Partial<SalarierDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  numArticleL143?: string|null;
  nin?: string|null;
  prenom?: string|null;
  nom?: string|null;
  dateNaissane?: string|null;
  lieuNaissance?: string|null;
  nomMereComplet?: string|null;
  prenomPere?: string|null;
  adresse?: string|null;
  email?: string|null;
  telephone1?: string|null;
  telephone2?: string|null;
  compteBancaire?: string|null;
  dateEmbauche?: string|null;
  salaire?: number|null;
  debutConge?: string|null;
  site?: number|null;

}
