package sn.css.c_s_s_conge.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.model.NUserDTO;
import sn.css.c_s_s_conge.service.DmtService;
import sn.css.c_s_s_conge.service.NUserService;
import sn.css.c_s_s_conge.util.NotFoundException;

/**
 * Contrôleur REST pour gérer les requêtes liées aux user-admin.
 * Ce contrôleur fournit des endpoints pour créer, lire, mettre à jour et supprimer des users.
 */
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@RequiredArgsConstructor
public class NUserResource {

    private final NUserService userService;

    /**
     * Obtient un User par son identifiant.
     *
     * @param email L'email du User.
     * @param password mot de passe du User.
     * @return Le ResponseEntity<NUserDTO>.
     * @throws NotFoundException Si le User n'est pas trouvé.
     */
    @GetMapping("/{email}/{password}")
    public ResponseEntity<NUserDTO> loginAdmin(@PathVariable(name = "email") final String email,
                                           @PathVariable(name = "password") final String password) {
        return ResponseEntity.ok(userService.loginAdmin(email, password));
    }
}
