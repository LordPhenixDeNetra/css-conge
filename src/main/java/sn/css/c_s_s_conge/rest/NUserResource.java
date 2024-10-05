package sn.css.c_s_s_conge.rest;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.model.LoginRequest;
import sn.css.c_s_s_conge.model.NUserDTO;
import sn.css.c_s_s_conge.model.SalarierDTO;
import sn.css.c_s_s_conge.service.DmtService;
import sn.css.c_s_s_conge.service.NUserService;
import sn.css.c_s_s_conge.util.NotFoundException;

import java.util.List;

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
     * Récupère toutes les Users disponibles.
     *
     * @return Une liste de UserDTO représentant toutes les Users.
     */
    @GetMapping
    public ResponseEntity<List<NUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Obtient un User par ses identifiants.
     *
     * @param loginRequest .
     * @return Le ResponseEntity<NUserDTO>.
     * @throws NotFoundException Si le User n'est pas trouvé.
     */
    @PostMapping("/login")
    public ResponseEntity<NUserDTO> loginAdmin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginAdmin(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    /**
     * Pour valider le DMT salarié.
     * @param dmtDTO du salarié à créer.
     * @return Identifiant du salarié créé.
     */
    @PostMapping("/validateDMT")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSalarier(@RequestBody @Valid final DmtDTO dmtDTO) {
        final Long createdId = userService.validateDMT(dmtDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

}
