import { Injectable } from '@angular/core';
import * as bcrypt from 'bcryptjs';

@Injectable({
  providedIn: 'root',
})
export class HashService {
  constructor() {}

  // Méthode pour hacher un mot de passe
  hashString(plainText: string): string {
    // Générer un salt (par défaut 10 rounds)
    // const salt = bcrypt.genSaltSync(10);

    const salt = bcrypt.genSaltSync(12); // 12 rounds au lieu de 10

    // Hacher le mot de passe avec le salt
    const hashedString = bcrypt.hashSync(plainText, salt);
    return hashedString;
  }

  // Méthode pour comparer un texte en clair avec un hash
  compareString(plainText: string, hash: string): boolean {
    return bcrypt.compareSync(plainText, hash);
  }
}
