package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object pour les messages.
 * <p>
 * Cette classe est utilisée pour transférer les données des messages entre les couches de l'application.
 * </p>
 */
@Getter
@Setter
public class NMessageDTO {

    /**
     * Identifiant unique du message.
     */
    private Long id;

    /**
     * Contenu du message.
     */
    @NotNull
    private String message;

    /**
     * Identifiant du salarié destinataire du message.
     */
    private Long salarier;

    /**
     * Identifiant de l'utilisateur expéditeur du message.
     */
    private Long user;
}
