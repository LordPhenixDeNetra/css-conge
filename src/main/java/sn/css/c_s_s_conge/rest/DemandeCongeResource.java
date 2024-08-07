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
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.model.DemandeCongeDTO;
import sn.css.c_s_s_conge.model.DemandeStatus;
import sn.css.c_s_s_conge.repos.DossierRepository;
import sn.css.c_s_s_conge.repos.SalarierRepository;
import sn.css.c_s_s_conge.service.DemandeCongeService;
import sn.css.c_s_s_conge.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/demandeConges", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DemandeCongeResource {

    private final DemandeCongeService demandeCongeService;
    private final SalarierRepository salarierRepository;
    private final DossierRepository dossierRepository;

    public DemandeCongeResource(final DemandeCongeService demandeCongeService,
            final SalarierRepository salarierRepository,
            final DossierRepository dossierRepository) {
        this.demandeCongeService = demandeCongeService;
        this.salarierRepository = salarierRepository;
        this.dossierRepository = dossierRepository;
    }

    @GetMapping
    public ResponseEntity<List<DemandeCongeDTO>> getAllDemandeConges() {
        return ResponseEntity.ok(demandeCongeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeCongeDTO> getDemandeConge(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(demandeCongeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDemandeConge(
            @RequestBody @Valid final DemandeCongeDTO demandeCongeDTO) {
        final Long createdId = demandeCongeService.create(demandeCongeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PostMapping("/{salarierId}/{dossierId}")
    public ResponseEntity<DemandeCongeDTO> createWithSalarierAndDossier(@RequestBody final DemandeCongeDTO demandeCongeDTO, @PathVariable Long salarierId, @PathVariable Long dossierId) {
        final DemandeCongeDTO demandeConge = demandeCongeService.createWithSalarierAndDossier(demandeCongeDTO, salarierId, dossierId);
        return new ResponseEntity<>(demandeConge, HttpStatus.CREATED);
    }

    @GetMapping("/salarier/{salarierId}")
    public ResponseEntity<DemandeCongeDTO> findBySalarierId(
        @PathVariable(name = "salarierId") final Long salarierId) {
        final DemandeCongeDTO demandeConge = demandeCongeService.findBySalarierId(salarierId);
        return new ResponseEntity<>(demandeConge, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDemandeConge(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DemandeCongeDTO demandeCongeDTO) {
        demandeCongeService.update(id, demandeCongeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDemandeConge(@PathVariable(name = "id") final Long id) {
        demandeCongeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salarierValues")
    public ResponseEntity<Map<Long, String>> getSalarierValues() {
        return ResponseEntity.ok(salarierRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Salarier::getId, Salarier::getNumArticleL143)));
    }

    @GetMapping("/dossierValues")
    public ResponseEntity<Map<Long, Long>> getDossierValues() {
        return ResponseEntity.ok(dossierRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Dossier::getId, Dossier::getId)));
    }

}
