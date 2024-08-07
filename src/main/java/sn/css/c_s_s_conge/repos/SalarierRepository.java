package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.domain.Site;


public interface SalarierRepository extends JpaRepository<Salarier, Long> {

//    Salarier findFirstBySite(Site site);

    Salarier findSalarierByNin(String nin);

    boolean existsByNinIgnoreCase(String nin);

}
