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
 * Représente un utilisateur dans l'application.
 * Cette classe est une entité JPA et sera stockée dans une base de données relationnelle.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "users")
public class NUser {

    /**
     * Identifiant unique de l'utilisateur.
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
     * Prénom de l'utilisateur.
     */
    @Column
    private String prenom;

    /**
     * Nom de l'utilisateur.
     */
    @Column
    private String nom;

    /**
     * Type de profil de l'utilisateur.
     */
    @Column(name = "type_profil")
    private String typeProfil;

    /**
     * Adresse e-mail de l'utilisateur.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Mot de passe chiffré de l'utilisateur.
     */
    @Column(name = "encrypted_password")
    private String password;

    /**
     * Numéro CSS de l'utilisateur.
     */
    @Column(name = "num_css")
    private String numCSS;

    /**
     * Numéro d'immatriculation de l'utilisateur.
     */
    @Column(name = "num_immatriculation")
    private String numImmatriculation;

    /**
     * Indique si l'utilisateur est actif ou non.
     */
    @Column(nullable = false)
    private boolean actif;

    /**
     * Agence CSS de l'utilisateur.
     */
    @Column(name = "agence_css")
    private String agenceCSS;

    /**
     * Sexe de l'utilisateur (0 pour masculin, 1 pour féminin).
     */
    @Column
    private Long sexe;

    /**
     * Numéro d'identification national de l'utilisateur.
     */
    @Column
    private String nin;

    /**
     * Date de naissance de l'utilisateur.
     */
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    /**
     * Lieu de naissance de l'utilisateur.
     */
    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    /**
     * Date de création de l'entité.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    /**
     * Date de dernière modification de l'entité.
     */
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;

}
