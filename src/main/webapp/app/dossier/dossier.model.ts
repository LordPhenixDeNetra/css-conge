export class DossierDTO {

  constructor(data:Partial<DossierDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  attestationTravail?: string|null;
  attestationCessationPaie?: string|null;
  certificatMedical?: string|null;
  dernierBulletinSalaire?: string|null;
  copieCNI?: string|null;

}
