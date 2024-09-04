package sn.css.c_s_s_conge.service.interfaces;

/**
 * Interface pour la gestion des services d'envoi d'emails.
 * <p>
 * Cette interface définit les méthodes pour l'envoi d'emails dans l'application.
 * </p>
 */
public interface EmailService {

    /**
     * Envoie un message email avec un statut.
     *
     * @param name   Nom du destinataire.
     * @param to     Adresse email du destinataire.
     * @param status Statut à inclure dans le message email.
     */
    void sendMessage(String name, String to, String status);
}
