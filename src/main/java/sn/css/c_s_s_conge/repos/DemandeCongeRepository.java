package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.domain.Salarier;


public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    DemandeConge findFirstBySalarier(Salarier salarier);

    DemandeConge findFirstByDossier(Dossier dossier);

}
