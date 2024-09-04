package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Objet de transfert de données (DTO) pour un dossier.
 * <p>
 * Utilisé pour transférer les données d'un dossier entre les couches de l'application.
 * </p>
 */
@Getter
@Setter
public class DossierDTO {

    /**
     * Identifiant unique du dossier.
     */
    private Long id;

    /**
     * Attestation de travail.
     */
    @NotNull
    private String attestationTravail;

    /**
     * Attestation de cessation de paiement.
     */
    @NotNull
    private String attestationCessationPaie;

    /**
     * Certificat médical.
     */
    @NotNull
    private String certificatMedical;

    /**
     * Dernier bulletin de salaire.
     */
    @NotNull
    private String dernierBulletinSalaire;

    /**
     * Copie de la carte nationale d'identité.
     */
    @NotNull
    private String copieCNI;
}
