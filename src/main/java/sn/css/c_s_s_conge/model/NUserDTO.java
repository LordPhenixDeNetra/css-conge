package sn.css.c_s_s_conge.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Objet de transfert de données représentant un utilisateur.
 */
@Getter
@Setter
public class NUserDTO {

    /**
     * Identifiant unique de l'utilisateur.
     */
    private Long id;

    /**
     * Prénom de l'utilisateur.
     */
    private String prenom;

    /**
     * Nom de l'utilisateur.
     */
    private String nom;

    /**
     * Type de profil de l'utilisateur.
     */
    private String typeProfil;

    /**
     * Adresse e-mail de l'utilisateur.
     */
    private String email;

    /**
     * Mot de passe de l'utilisateur.
     */
    private String password;

    /**
     * Numéro CSS de l'utilisateur.
     */
    private String numCSS;

    /**
     * Numéro d'immatriculation de l'utilisateur.
     */
    private String numImmatriculation;

    /**
     * Indique si l'utilisateur est actif ou non.
     */
    private boolean actif;

    /**
     * Agence CSS de l'utilisateur.
     */
    private String agenceCSS;

    /**
     * Sexe de l'utilisateur (0 pour masculin, 1 pour féminin).
     */
    private Long sexe;

    /**
     * Numéro d'identification national de l'utilisateur.
     */
    private String nin;

    /**
     * Date de naissance de l'utilisateur.
     */
    private LocalDate dateNaissance;

    /**
     * Lieu de naissance de l'utilisateur.
     */
    private String lieuNaissance;

}
