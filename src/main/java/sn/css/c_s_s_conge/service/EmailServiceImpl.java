package sn.css.c_s_s_conge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.service.interfaces.EmailService;
import sn.css.c_s_s_conge.util.EmailUtils;

/**
 * Implémentation du service d'envoi d'emails.
 * <p>
 * Cette classe fournit la logique pour envoyer des emails via le service de messagerie.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "Demande de congé de maternité";
    public static final String DMT_VALIDATION = "Validation de DMT";
    public static final String DMT_NON_VALIDATION = "Non Validation de DMT";

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    /**
     * Envoie un message email avec le statut fourni.
     * <p>
     * Cette méthode est exécutée de manière asynchrone pour éviter de bloquer le thread appelant.
     * </p>
     *
     * @param name   Nom du destinataire.
     * @param to     Adresse email du destinataire.
     * @param status Statut à inclure dans le message email.
     */
    @Override
    @Async
    public void sendMessage(String name, String to, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getSimpleMessage(name, status));

            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Envoie un message email avec le statut fourni.
     * <p>
     * Cette méthode est exécutée de manière asynchrone pour éviter de bloquer le thread appelant.
     * </p>
     *
     * @param name   Nom du destinataire.
     * @param to     Adresse email du destinataire.
     */
    @Async
    @Override
    public void sendMessageForDMTValidation(String name, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(DMT_VALIDATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getSimpleMessageForDMTValidation(name));

            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Envoie un message email avec le statut fourni.
     * <p>
     * Cette méthode est exécutée de manière asynchrone pour éviter de bloquer le thread appelant.
     * </p>
     *
     * @param name   Nom du destinataire.
     * @param to     Adresse email du destinataire.
     */
    @Async
    @Override
    public void sendMessageForDMTNotValidation(String name, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(DMT_NON_VALIDATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getSimpleMessageForDMTNonValidation(name));

            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
