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

    @GetMapping
    public ResponseEntity<List<SalarierDTO>> getAllSalariers() {
        return ResponseEntity.ok(salarierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalarierDTO> getSalarier(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(salarierService.get(id));
    }

    @GetMapping("/getByNin/{nin}")
    public ResponseEntity<SalarierDTO> getSalarierByNin(@PathVariable(name = "nin") final String nin) {
        return ResponseEntity.ok(salarierService.findSalarierByNin(nin));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSalarier(@RequestBody @Valid final SalarierDTO salarierDTO) {
        final Long createdId = salarierService.create(salarierDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSalarier(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SalarierDTO salarierDTO) {
        salarierService.update(id, salarierDTO);
        return ResponseEntity.ok(id);
    }

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
