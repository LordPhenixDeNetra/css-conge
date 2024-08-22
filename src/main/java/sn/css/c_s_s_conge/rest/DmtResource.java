package sn.css.c_s_s_conge.rest;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.service.DmtService;
import sn.css.c_s_s_conge.util.ReferencedException;
import sn.css.c_s_s_conge.util.ReferencedWarning;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/dmts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DmtResource {

    private final DmtService dmtService;

    public DmtResource(final DmtService dmtService) {
        this.dmtService = dmtService;
    }

    @GetMapping
    public ResponseEntity<List<DmtDTO>> getAllDmts() {
        return ResponseEntity.ok(dmtService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DmtDTO> getDmt(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dmtService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDmt(@RequestBody @Valid final DmtDTO dmtDTO) {
        final Long createdId = dmtService.create(dmtDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

//    @PostMapping("/file")
//    @ApiResponse(responseCode = "201")
//    public ResponseEntity<Long> createDmtWithFile(@RequestBody @Valid final DmtDTO dmtDTO, @RequestParam("file") MultipartFile file) {
//        final Long createdId = dmtService.createWithFile(dmtDTO, file);
//        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//    }

    @PostMapping("/file")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDmtWithFile(
        @RequestParam("numArticleL143") String numArticleL143,
        @RequestParam("nin") String nin,
        @RequestParam("prenom") String prenom,
        @RequestParam("nom") String nom,
        @RequestParam("dateNaissane") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateNaissane,
        @RequestParam("lieuNaissance") String lieuNaissance,
        @RequestParam("adresse") String adresse,
        @RequestParam("email") String email,
        @RequestParam("telephone1") String telephone1,
        @RequestParam("file") MultipartFile file) {

        DmtDTO dmtDTO = new DmtDTO();
        dmtDTO.setNumArticleL143(numArticleL143);
        dmtDTO.setNin(nin);
        dmtDTO.setPrenom(prenom);
        dmtDTO.setNom(nom);
        dmtDTO.setDateNaissane(dateNaissane);
        dmtDTO.setLieuNaissance(lieuNaissance);
        dmtDTO.setAdresse(adresse);
        dmtDTO.setEmail(email);
        dmtDTO.setTelephone1(telephone1);

        final Long createdId = dmtService.createWithFile(dmtDTO, file);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDmt(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final DmtDTO dmtDTO) {
        dmtService.update(id, dmtDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDmt(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = dmtService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dmtService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/siteValues")
//    public ResponseEntity<Map<Long, Long>> getSiteValues() {
//        return ResponseEntity.ok(siteRepository.findAll(Sort.by("id"))
//            .stream()
//            .collect(CustomCollectors.toSortedMap(Site::getId, Site::getId)));
//    }

}
