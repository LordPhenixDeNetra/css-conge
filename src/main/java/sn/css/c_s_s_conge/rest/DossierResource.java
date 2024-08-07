package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.css.c_s_s_conge.model.DossierDTO;
import sn.css.c_s_s_conge.service.DossierService;
import sn.css.c_s_s_conge.util.ReferencedException;
import sn.css.c_s_s_conge.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/dossiers", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DossierResource {

    private final DossierService dossierService;

    public DossierResource(final DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @GetMapping
    public ResponseEntity<List<DossierDTO>> getAllDossiers() {
        return ResponseEntity.ok(dossierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierDTO> getDossier(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dossierService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDossier(@RequestBody @Valid final DossierDTO dossierDTO) {
        final Long createdId = dossierService.create(dossierDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PostMapping("/upload/files")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<DossierDTO> createDossierWithFile(@RequestParam("files") List<MultipartFile> files) {
        final DossierDTO dossierDTO = dossierService.ajouterFilesDossier(files);
        return ResponseEntity.ok(dossierDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDossier(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DossierDTO dossierDTO) {
        dossierService.update(id, dossierDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDossier(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = dossierService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dossierService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
