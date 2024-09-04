package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.domain.Salarier;

/**
 * Repository pour l'entité DemandeConge.
 * Fournit des méthodes pour interagir avec la base de données, en particulier pour rechercher
 * des demandes de congé par salarié ou dossier.
 */
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    /**
     * Trouve la première demande de congé associée à un salarié donné.
     *
     * @param salarier Le salarié dont on veut trouver la première demande de congé.
     * @return La première demande de congé trouvée pour le salarié.
     */
    DemandeConge findFirstBySalarier(Salarier salarier);

    /**
     * Trouve la première demande de congé associée à un dossier donné.
     *
     * @param dossier Le dossier dont on veut trouver la première demande de congé.
     * @return La première demande de congé trouvée pour le dossier.
     */
    DemandeConge findFirstByDossier(Dossier dossier);

}
