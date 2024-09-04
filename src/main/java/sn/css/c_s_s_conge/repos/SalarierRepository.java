package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.domain.Site;


/**
 * Interface de repository pour gérer les opérations CRUD sur les entités `Salarier`.
 * Extends JpaRepository pour bénéficier de méthodes prédéfinies de gestion de données.
 */
public interface SalarierRepository extends JpaRepository<Salarier, Long> {

//    Salarier findFirstBySite(Site site);

    /**
     * Recherche un salarié par son NIN.
     * @param nin Numéro d'identification national.
     * @return L'entité `Salarier` correspondante.
     */
    Salarier findSalarierByNin(String nin);

    /**
     * Vérifie l'existence d'un salarié par son NIN (insensible à la casse).
     * @param nin Numéro d'identification national.
     * @return true si un salarié avec le NIN donné existe, false sinon.
     */
    boolean existsByNinIgnoreCase(String nin);

}
