package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.domain.Site;
import sn.css.c_s_s_conge.model.SalarierDTO;
import sn.css.c_s_s_conge.repos.SiteRepository;
import sn.css.c_s_s_conge.service.SalarierService;
import sn.css.c_s_s_conge.util.CustomCollectors;
import sn.css.c_s_s_conge.util.ReferencedException;
import sn.css.c_s_s_conge.util.ReferencedWarning;

/**
 * Contrôleur REST pour gérer les opérations sur les entités `Salarier`.
 * Fournit des endpoints pour créer, lire, mettre à jour et supprimer des salariés.
 */
@RestController
@RequestMapping(value = "/api/salariers", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class SalarierResource {

    private final SalarierService salarierService;
    private final SiteRepository siteRepository;


    public SalarierResource(final SalarierService salarierService,
            final SiteRepository siteRepository) {
        this.salarierService = salarierService;
        this.siteRepository = siteRepository;
    }

    /**
     * Récupère tous les salariés.
     * @return Liste des DTOs des salariés.
     */
    @GetMapping
    public ResponseEntity<List<SalarierDTO>> getAllSalariers() {
        return ResponseEntity.ok(salarierService.findAll());
    }

    /**
     * Récupère un salarié par son identifiant.
     * @param id Identifiant du salarié.
     * @return DTO du salarié.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalarierDTO> getSalarier(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(salarierService.get(id));
    }

    /**
     * Récupère un salarié par son NIN.
     * @param nin Numéro d'identification national.
     * @return DTO du salarié.
     */
    @GetMapping("/getByNin/{nin}")
    public ResponseEntity<SalarierDTO> getSalarierByNin(@PathVariable(name = "nin") final String nin) {
        return ResponseEntity.ok(salarierService.findSalarierByNin(nin));
    }

    /**
     * Crée un nouveau salarié.
     * @param salarierDTO DTO du salarié à créer.
     * @return Identifiant du salarié créé.
     */
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSalarier(@RequestBody @Valid final SalarierDTO salarierDTO) {
        final Long createdId = salarierService.create(salarierDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    /**
     * Met à jour un salarié existant.
     * @param id Identifiant du salarié.
     * @param salarierDTO DTO du salarié à mettre à jour.
     * @return Identifiant du salarié mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSalarier(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SalarierDTO salarierDTO) {
        salarierService.update(id, salarierDTO);
        return ResponseEntity.ok(id);
    }

    /**
     * Supprime un salarié par son identifiant.
     * @param id Identifiant du salarié à supprimer.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSalarier(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = salarierService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        salarierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/siteValues")
    public ResponseEntity<Map<Long, Long>> getSiteValues() {
        return ResponseEntity.ok(siteRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Site::getId, Site::getId)));
    }

}
