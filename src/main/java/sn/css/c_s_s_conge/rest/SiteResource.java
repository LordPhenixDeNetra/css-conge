package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.model.SiteDTO;
import sn.css.c_s_s_conge.service.SiteService;
import sn.css.c_s_s_conge.util.ReferencedException;
import sn.css.c_s_s_conge.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/sites", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class SiteResource {

    private final SiteService siteService;

    public SiteResource(final SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public ResponseEntity<List<SiteDTO>> getAllSites() {
        return ResponseEntity.ok(siteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getSite(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(siteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSite(@RequestBody @Valid final SiteDTO siteDTO) {
        final Long createdId = siteService.create(siteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSite(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SiteDTO siteDTO) {
        siteService.update(id, siteDTO);
        return ResponseEntity.ok(id);
    }

    /*
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSite(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = siteService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        siteService.delete(id);
        return ResponseEntity.noContent().build();
    }

     */

}
