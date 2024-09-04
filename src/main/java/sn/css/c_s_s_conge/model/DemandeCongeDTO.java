package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de transfert de données (DTO) représentant une demande de congé.
 *
 * .@Getter et @Setter sont des annotations Lombok permettant de générer automatiquement les méthodes
 * getter et setter pour les champs de la classe.
 */
@Getter
@Setter
public class DemandeCongeDTO {

    /**
     * L'identifiant unique de la demande de congé.
     */
    private Long id;

    /**
     * Le statut actuel de la demande de congé.
     * Cette valeur ne doit pas être nulle.
     */
    @NotNull
    private DemandeStatus status;

    /**
     * L'identifiant du salarié associé à la demande de congé.
     */
    private Long salarier;

    /**
     * L'identifiant du dossier associé à la demande de congé.
     */
    private Long dossier;

}
