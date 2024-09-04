package sn.css.c_s_s_conge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entité représentant un dossier dans le système.
 * <p>
 * Un dossier contient des informations telles que des attestations et des certificats liés à un employé.
 * </p>
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Dossier {

    /**
     * Identifiant unique du dossier.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
        name = "primary_sequence",
        sequenceName = "primary_sequence",
        allocationSize = 1,
        initialValue = 10000
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "primary_sequence"
    )
    private Long id;

    /**
     * Attestation de travail du dossier.
     */
    @Column(nullable = false, columnDefinition = "text")
    private String attestationTravail;

    /**
     * Attestation de cessation de paiement du dossier.
     */
    @Column(nullable = false, columnDefinition = "text")
    private String attestationCessationPaie;

    /**
     * Certificat médical du dossier.
     */
    @Column(nullable = false, columnDefinition = "text")
    private String certificatMedical;

    /**
     * Dernier bulletin de salaire du dossier.
     */
    @Column(nullable = false, columnDefinition = "text")
    private String dernierBulletinSalaire;

    /**
     * Copie de la carte nationale d'identité du dossier.
     */
    @Column(nullable = false, columnDefinition = "text")
    private String copieCNI;

    /**
     * Liste des demandes de congé associées à ce dossier.
     */
    @OneToMany(mappedBy = "dossier")
    private Set<DemandeConge> demandeConges;

    /**
     * Date de création du dossier.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    /**
     * Date de la dernière mise à jour du dossier.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
