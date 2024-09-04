package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.model.NMessageDTO;
import sn.css.c_s_s_conge.service.NMessageService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des messages.
 * <p>
 * Ce contrôleur fournit des endpoints pour créer et obtenir des messages.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@RequiredArgsConstructor
public class NMessageResource {

    private final NMessageService messageService;

    /**
     * Crée un nouveau message.
     *
     * @param nMessageDTO Le DTO contenant les informations du message.
     * @return Une réponse contenant l'identifiant du message créé.
     */
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMessage(@RequestBody @Valid final NMessageDTO nMessageDTO) {
        final Long createdId = messageService.create(nMessageDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    /**
     * Crée un nouveau message avec un expéditeur et un destinataire spécifiés.
     *
     * @param nMessageDTO Le DTO contenant les informations du message.
     * @param sender     Identifiant de l'expéditeur.
     * @param receiver   Identifiant du destinataire.
     * @return Une réponse contenant l'identifiant du message créé.
     */
    @PostMapping("/sendMessage/{sender}/{receiver}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createWithSenderAndReceiver(@RequestBody @Valid final NMessageDTO nMessageDTO, @PathVariable Long sender, @PathVariable Long receiver) {
        final Long createdId = messageService.createWithSenderAndReceiver(nMessageDTO, sender, receiver);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    /**
     * Obtient tous les messages associés à un salarié par son identifiant.
     *
     * @param idSalarier L'identifiant du salarié.
     * @return Une réponse contenant la liste des DTOs des messages associés au salarié.
     */
    @GetMapping("/{idSalarier}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<List<NMessageDTO>> findAllMessageBySalarierId(@PathVariable Long idSalarier) {
        return ResponseEntity.ok(messageService.findAllMessageBySalarierId(idSalarier));
    }


}
