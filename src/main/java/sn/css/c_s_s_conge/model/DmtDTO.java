package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) pour la DMT.
 * Utilisé pour transférer des données entre les couches de l'application sans exposer l'entité directement.
 */
@Getter
@Setter
public class DmtDTO {

    /**
     * Identifiant unique de la DMT.
     */
    private Long id;

    /**
     * Le numéro d'allocataire associé à la DMT.
     */
    @NotNull
    @Size(max = 20)
    private String numArticleL143;

    /**
     * Numéro d'Identification National (NIN) associé à la DMT.
     */
    @NotNull
    @Size(max = 25)
    @SalarierNinUnique
    private String nin;

    /**
     * Prénom de la personne associée à la DMT.
     */
    @Size(max = 100)
    private String prenom;

    /**
     * Nom de la personne associée à la DMT.
     */
    @Size(max = 25)
    private String nom;

    /**
     * Date de naissance de la personne associée à la DMT.
     */
    private LocalDate dateNaissane;

    /**
     * Lieu de naissance de la personne associée à la DMT.
     */
    @Size(max = 255)
    private String lieuNaissance;

    /**
     * Adresse de la personne associée à la DMT.
     */
    @Size(max = 255)
    private String adresse;

    /**
     * Adresse e-mail de la personne associée à la DMT.
     */
    @NotNull
    @Size(max = 100)
    private String email;

    /**
     * Numéro de téléphone de la personne associée à la DMT.
     */
    @NotNull
    @Size(max = 100)
    private String telephone1;

    /**
     * Document associé à la DMT.
     */
    @NotNull
    @Size(max = 255)
    private String document;
}
