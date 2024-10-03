package sn.css.c_s_s_conge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.css.c_s_s_conge.domain.NUser;

import java.util.Optional;

/**
 * Interface de repository pour gérer les entités NUser dans la base de données.
 */
public interface NUserRepository extends JpaRepository<NUser, Long> {
    Optional<NUser> findNUserByEmailIgnoreCaseAndPassword(String email, String password);
    NUser findNUserByEmailIgnoreCaseAndActifTrue(String email);
//    Optional<NUser> findNUserByEmailIgnoreCaseAndPasswordAndActifTrue(String email, String password);

}
