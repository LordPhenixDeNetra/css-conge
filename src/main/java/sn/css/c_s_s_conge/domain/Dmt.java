package sn.css.c_s_s_conge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Entité représentant une Demande de Mutation Temporaire (DMT) dans le système.
 * Cette entité contient des informations personnelles et administratives relatives à une demande de mutation temporaire.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "n_dmt")
public class Dmt {

    /**
     * Identifiant unique pour la DMT.
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
     * Le numéro d'allocataire associé à la DMT.
     */
    @Column(nullable = false, name = "n_dmt_numero_allocataire")
    private String numArticleL143;

    /**
     * Numéro d'Identification National (NIN) associé à la DMT.
     */
    @Column(nullable = false, name = "n_dmt_nin")
    private String nin;

    /**
     * Prénom de la personne associée à la DMT.
     */
    @Column
    private String prenom;

    /**
     * Nom de la personne associée à la DMT.
     */
    @Column
    private String nom;

    /**
     * Date de naissance de la personne associée à la DMT.
     */
    @Column(name = "n_dmt_date_naissance")
    private LocalDate dateNaissane;

    /**
     * Lieu de naissance de la personne associée à la DMT.
     */
    @Column(name = "n_dmt_lieu_naissance")
    private String lieuNaissance;

    /**
     * Adresse de la personne associée à la DMT.
     */
    @Column(name = "n_dmt_adresse_ville")
    private String adresse;

    /**
     * Adresse e-mail de la personne associée à la DMT.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Numéro de téléphone de la personne associée à la DMT.
     */
    @Column(nullable = false, name = "n_dmt_telephone")
    private String telephone1;

    /**
     * Document associé à la DMT.
     */
    @Column(nullable = false, name = "n_dmt_document")
    private String document;

    /**
     * Date de création de la DMT. Gérée automatiquement par le système.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    /**
     * Date de la dernière mise à jour de la DMT. Gérée automatiquement par le système.
     */
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;

}
