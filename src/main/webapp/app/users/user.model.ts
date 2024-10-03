export class NUserDTO {

  constructor(data: Partial<NUserDTO>) {
    Object.assign(this, data);
  }

  id?: number | null;
  prenom?: string | null;
  nom?: string | null;
  typeProfil?: string | null;
  email?: string | null;
  password?: string | null;
  numCSS?: string | null;
  numImmatriculation?: string | null;
  actif?: boolean | null;
  agenceCSS?: string | null;
  sexe?: number | null;  // 0 pour masculin, 1 pour féminin
  nin?: string | null;
  dateNaissance?: string | null;  // Remplace LocalDate par string pour TypeScript
  lieuNaissance?: string | null;

}
