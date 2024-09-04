package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.NMessage;
import sn.css.c_s_s_conge.domain.Salarier;

import java.util.List;

/**
 * Repository pour les opérations CRUD sur les entités NMessage.
 * <p>
 * Ce repository fournit des méthodes pour accéder aux messages basés sur le salarié destinataire.
 * </p>
 */
public interface NMessageRepository extends JpaRepository<NMessage, Long> {

    /**
     * Trouve un message basé sur le salarié destinataire.
     *
     * @param salarier Le salarié destinataire du message.
     * @return Le message associé au salarié.
     */
    NMessage findNMessageBySalarier(Salarier salarier);

    /**
     * Trouve tous les messages associés à un salarié.
     *
     * @param salarier Le salarié destinataire des messages.
     * @return La liste des messages associés au salarié.
     */
    List<NMessage> findAllBySalarier(Salarier salarier);
}
