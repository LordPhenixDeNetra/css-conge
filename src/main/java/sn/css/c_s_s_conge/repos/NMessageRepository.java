package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.NMessage;
import sn.css.c_s_s_conge.domain.Salarier;

import java.util.List;

public interface NMessageRepository extends JpaRepository<NMessage, Long> {

    NMessage findNMessageBySalarier(Salarier salarier);
    List<NMessage> findAllBySalarier(Salarier salarier);

}
