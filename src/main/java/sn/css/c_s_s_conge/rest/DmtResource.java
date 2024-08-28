package sn.css.c_s_s_conge.rest;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/dmts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DmtResource {

    private final DmtService dmtService;

    @Value("${file.upload.dmt}")
    private String fileUploadDmt;

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

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "id") final Long id) {
        // Obtenez le DmtDTO avec le document
        DmtDTO dmtDTO = dmtService.get(id);
        String fileName = dmtDTO.getDocument();

        if (fileName == null) {
            return ResponseEntity.notFound().build();
        }

        // Créez le chemin du fichier
        Path filePath = Path.of(fileUploadDmt + fileName);
        try {
            Resource file = new UrlResource(filePath.toUri());
            if (file.exists() || file.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .body(file);
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error determining file type: " + fileName, e);
        }
    }

}
