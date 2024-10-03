package sn.css.c_s_s_conge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.NUser;
import sn.css.c_s_s_conge.model.NUserDTO;
import sn.css.c_s_s_conge.repos.NUserRepository;
import sn.css.c_s_s_conge.util.NotFoundException;

import java.util.List;

/**
 * Service pour la gestion des utilisateurs Admin.
 * <p>
 * Ce service fournit des méthodes pour créer, obtenir et mapper des user-admin entre les entités et les DTOs.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class NUserService {

    private final NUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crée un nouveau User à partir d'un DTO.
     *
     * @param UserDTO Le DTO contenant les informations du User.
     * @return L'identifiant du User créé.
     */
    public Long create(final NUserDTO UserDTO) {
        final NUser User = new NUser();
        mapToEntity(UserDTO, User);
        return userRepository.save(User).getId();
    }

    /**
     * Récupère toutes les NUsers triées par ID.
     *
     * @return Une liste de NUserDTO représentant toutes les NUsers.
     */
    public List<NUserDTO> findAll() {
        final List<NUser> nUsers = userRepository.findAll(Sort.by("id"));
        return nUsers.stream()
            .map(nUser -> mapToDTO(nUser, new NUserDTO()))
            .toList();
    }

    /**
     * Obtient un User par son identifiant.
     *
     * @param id L'identifiant du User.
     * @return Le DTO du User.
     * @throws NotFoundException Si le User n'est pas trouvé.
     */
    public NUserDTO get(final Long id) {
        return userRepository.findById(id)
            .map(user -> mapToDTO(user, new NUserDTO()))
            .orElseThrow(NotFoundException::new);
    }

    /**
     * Obtient un User par son identifiant.
     *
     * @param email L'email du User.
     * @param password mot de passe du User.
     * @return Le DTO du User.
     * @throws NotFoundException Si le User n'est pas trouvé.
     */
    public NUserDTO loginAdmin(String email, String password) {
        NUser user = userRepository.findNUserByEmailIgnoreCaseAndActifTrue(email);
        if (user != null && verifyPassword(password, user.getPassword())){
            System.out.println("============================================");
            System.out.println(verifyPassword(password, user.getPassword()));
            System.out.println("============================================");

            return mapToDTO(user, new NUserDTO());
        }
        System.out.println("============================================");
        System.out.println("password send: " + password);
        System.out.println("password from BD : " + user.getPassword());
        System.out.println(verifyPassword(password, user.getPassword()));
        System.out.println("============================================");
        return null;
    }

    public boolean verifyPassword(String rawPassword, String storedHash) {
        return passwordEncoder.matches(rawPassword, storedHash);
    }

    // Pour créer un nouveau hash lors de l'inscription
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


    /**
     * Mappe une entité NUser vers un DTO NUserDTO.
     *
     * @param nUser     L'entité NUser.
     * @param nUserDTO  Le DTO NUserDTO à remplir.
     * @return Le DTO rempli.
     */
    public NUserDTO mapToDTO(final NUser nUser, final NUserDTO nUserDTO) {
        nUserDTO.setId(nUser.getId());
        nUserDTO.setPrenom(nUser.getPrenom());
        nUserDTO.setNom(nUser.getNom());
        nUserDTO.setEmail(nUser.getEmail());
        nUserDTO.setPassword(nUser.getPassword());
        nUserDTO.setNin(nUser.getNin());
        nUserDTO.setNumCSS(nUser.getNumCSS());
        nUserDTO.setDateNaissance(nUser.getDateNaissance());
        nUserDTO.setLieuNaissance(nUser.getLieuNaissance());
        nUserDTO.setActif(nUser.isActif());
        nUserDTO.setNumImmatriculation(nUser.getNumImmatriculation());
        nUserDTO.setTypeProfil(nUser.getTypeProfil());
        nUserDTO.setSexe(nUser.getSexe());
        nUserDTO.setAgenceCSS(nUser.getAgenceCSS());
        return nUserDTO;
    }

    /**
     * Mappe un DTO NUserDTO vers une entité NUser.
     *
     * @param nUserDTO  Le DTO NUserDTO.
     * @param nUser     L'entité NUser à remplir.
     * @return L'entité remplie.
     * @throws NotFoundException Si le salarié ou l'utilisateur spécifié n'est pas trouvé.
     */
    public NUser mapToEntity(final NUserDTO nUserDTO, final NUser nUser) {
        nUser.setPrenom(nUserDTO.getPrenom());
        nUser.setNom(nUserDTO.getNom());
        nUser.setEmail(nUserDTO.getEmail());
        nUser.setPassword(nUserDTO.getPassword());
        nUser.setNin(nUserDTO.getNin());
        nUser.setNumCSS(nUserDTO.getNumCSS());
        nUser.setDateNaissance(nUserDTO.getDateNaissance());
        nUser.setLieuNaissance(nUserDTO.getLieuNaissance());
        nUser.setActif(nUserDTO.isActif());
        nUser.setNumImmatriculation(nUserDTO.getNumImmatriculation());
        nUser.setTypeProfil(nUserDTO.getTypeProfil());
        nUser.setSexe(nUserDTO.getSexe());
        nUser.setAgenceCSS(nUserDTO.getAgenceCSS());

        return nUser;
    }
}
