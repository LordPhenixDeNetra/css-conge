package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.model.DossierDTO;
import sn.css.c_s_s_conge.service.DossierService;
import sn.css.c_s_s_conge.util.ReferencedException;
import sn.css.c_s_s_conge.util.ReferencedWarning;

/**
 * Resource REST pour gérer les dossiers.
 * <p>
 * Fournit des endpoints pour effectuer des opérations CRUD sur les dossiers, ainsi que pour gérer les fichiers associés.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/dossiers", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DossierResource {

    private final DossierService dossierService;

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    /**
     * Constructeur pour le contrôleur {@link DossierResource}.
     *
     * @param dossierService Service pour gérer les dossiers.
     */
    public DossierResource(final DossierService dossierService) {
        this.dossierService = dossierService;
    }

    /**
     * Récupère tous les dossiers.
     *
     * @return Liste des DTO représentant tous les dossiers.
     */
    @GetMapping
    public ResponseEntity<List<DossierDTO>> getAllDossiers() {
        return ResponseEntity.ok(dossierService.findAll());
    }

    /**
     * Récupère un dossier par son identifiant.
     *
     * @param id Identifiant du dossier.
     * @return DTO représentant le dossier.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DossierDTO> getDossier(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dossierService.get(id));
    }

    /**
     * Crée un nouveau dossier.
     *
     * @param dossierDTO DTO représentant le dossier à créer.
     * @return Identifiant du dossier créé.
     */
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDossier(@RequestBody @Valid final DossierDTO dossierDTO) {
        final Long createdId = dossierService.create(dossierDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    /**
     * Crée un dossier avec des fichiers associés.
     *
     * @param files Liste de fichiers à ajouter au dossier.
     * @return DTO représentant le dossier avec les fichiers ajoutés.
     */
    @PostMapping("/upload/files")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<DossierDTO> createDossierWithFile(@RequestParam("files") List<MultipartFile> files) {
        final DossierDTO dossierDTO = dossierService.ajouterFilesDossier(files);
        return ResponseEntity.ok(dossierDTO);
    }

    /**
     * Met à jour un dossier existant.
     *
     * @param id Identifiant du dossier à mettre à jour.
     * @param dossierDTO DTO représentant les nouvelles données du dossier.
     * @return Identifiant du dossier mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDossier(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DossierDTO dossierDTO) {
        dossierService.update(id, dossierDTO);
        return ResponseEntity.ok(id);
    }

    /**
     * Supprime un dossier par son identifiant.
     *
     * @param id Identifiant du dossier à supprimer.
     * @return Réponse vide avec le statut HTTP 204 si la suppression a réussi.
     * @throws ReferencedException Si le dossier est référencé ailleurs et ne peut pas être supprimé.
     */
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

//    @GetMapping("/getfile/{fileName:.+}")
//    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
//        try {
//            Path filePath = Paths.get(fileUploadDir).resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                    .body(resource);
//            } else {
//                throw new FileNotFoundException("File not found " + fileName);
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("Error serving file " + fileName, ex);
//        }
//    }

    /**
     * Récupère un fichier par son nom.
     *
     * @param fileName Nom du fichier.
     * @return Ressource représentant le fichier.
     * @throws RuntimeException Si une erreur survient lors de la lecture du fichier.
     */
    @GetMapping("/getfile/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            // Créez le chemin du fichier
            Path filePath = Paths.get(fileUploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Déterminez le type de contenu du fichier
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error determining file type: " + fileName, e);
        }
    }

    /**
     * Récupère tous les fichiers associés à un dossier par son identifiant.
     *
     * @param fileId Identifiant du dossier.
     * @return Liste des noms de fichiers associés au dossier.
     * @throws RuntimeException Si une erreur survient lors de la récupération des fichiers.
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<List<String>> getAllFilesById(@PathVariable Long fileId) {

        DossierDTO dossierDTO = dossierService.get(fileId);
        List<String> fileUrls = new ArrayList<>();

        if (dossierDTO != null) {
            List<String> fileNames = Arrays.asList(
                dossierDTO.getAttestationTravail(),
                dossierDTO.getAttestationCessationPaie(),
                dossierDTO.getCertificatMedical(),
                dossierDTO.getDernierBulletinSalaire(),
                dossierDTO.getCopieCNI()
            );

            for (String fileName : fileNames) {
                try {
                    Path filePath = Paths.get(fileUploadDir).resolve(fileName).normalize();
                    System.out.println(filePath);
                    Resource resource = new UrlResource(filePath.toUri());
                    if (resource.exists()) {
//                        String fileUrl = generateFileUrl(fileName);
                        fileUrls.add(fileName);
                    } else {
                        throw new FileNotFoundException("File not found: " + fileName);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Error serving file: " + fileName, ex);
                }
            }

            return ResponseEntity.ok(fileUrls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Génère l'URL pour accéder à un fichier.
     *
     * @param fileName Nom du fichier.
     * @return URL du fichier.
     */
    private String generateFileUrl(String fileName) {
        String host = "http://localhost"; // ou utiliser votre nom de domaine
        int port = 8080; // remplacer par le port de votre application si nécessaire
        String contextPath = "/files"; // chemin de contexte où vous souhaitez servir les fichiers

        return host + ":" + port + contextPath + "/" + fileName;
    }



//    @GetMapping("/files/{fileId}")
//    public ResponseEntity<List<String>> getFileUrlsById(@PathVariable Long fileId) {
//        DossierDTO dossierDTO = dossierService.get(fileId);
//        if (dossierDTO != null) {
//            List<String> fileUrls = new ArrayList<>();
//            List<String> fileNames = List.of(
//                dossierDTO.getAttestationTravail(),
//                dossierDTO.getAttestationCessationPaie(),
//                dossierDTO.getCertificatMedical(),
//                dossierDTO.getDernierBulletinSalaire(),
//                dossierDTO.getCopieCNI()
//            );
//
//            for (String fileName : fileNames) {
//                Path filePath = Paths.get(fileUploadDir).resolve(fileName).normalize();
//                if (Files.exists(filePath)) {
//                    fileUrls.add(filePath.toUri().toString());
//                }
//            }
//
//            return ResponseEntity.ok().body(fileUrls);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
