package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.Dossier;

/**
 * Repository pour l'entité {@link Dossier}.
 * <p>
 * Fournit des méthodes pour effectuer des opérations CRUD sur les dossiers.
 * </p>
 */
public interface DossierRepository extends JpaRepository<Dossier, Long> {
}
