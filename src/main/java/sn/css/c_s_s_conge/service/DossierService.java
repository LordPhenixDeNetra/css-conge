package sn.css.c_s_s_conge.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.model.DossierDTO;
import sn.css.c_s_s_conge.repos.DemandeCongeRepository;
import sn.css.c_s_s_conge.repos.DossierRepository;
import sn.css.c_s_s_conge.service.interfaces.EmailService;
import sn.css.c_s_s_conge.util.NotFoundException;
import sn.css.c_s_s_conge.util.ReferencedWarning;


@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final DemandeCongeRepository demandeCongeRepository;

    // Répertoire de téléchargement des images, défini dans application.properties
    @Value("${file.upload.dir}")
    private String fileUploadDir;

    public DossierService(final DossierRepository dossierRepository,
            final DemandeCongeRepository demandeCongeRepository) {
        this.dossierRepository = dossierRepository;
        this.demandeCongeRepository = demandeCongeRepository;
    }

    public List<DossierDTO> findAll() {
        final List<Dossier> dossiers = dossierRepository.findAll(Sort.by("id"));
        return dossiers.stream()
                .map(dossier -> mapToDTO(dossier, new DossierDTO()))
                .toList();
    }

    // Méthode pour ajouter plusieurs fichiers
    public DossierDTO ajouterFilesDossier(List<MultipartFile> files) {

        List<String> dossierFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            // Récupération du type de contenu du fichier
            String contentType = file.getContentType();

            // Vérification du type de contenu du fichier
            if (contentType == null || (!contentType.equals("image/png") &&
                !contentType.equals("image/jpeg") &&
                !contentType.equals("image/jpg") &&
                !contentType.equals("image/gif")&&
                !contentType.equals("application/pdf"))) {
                // Si le type de contenu n'est pas supporté, une exception est levée on ne fait rien

            }else {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                dossierFiles.add(fileName);
                try {
                    // Enregistrez le fichier dans le dossier "images"
                    Path imagePath = Path.of(fileUploadDir + fileName);
                    Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
                }
            }
        }

        Dossier dossier = new Dossier();

        dossier.setAttestationTravail(dossierFiles.get(0));
        dossier.setAttestationCessationPaie(dossierFiles.get(1));
        dossier.setCertificatMedical(dossierFiles.get(2));
        dossier.setDernierBulletinSalaire(dossierFiles.get(3));
        dossier.setCopieCNI(dossierFiles.get(4));

        dossierRepository.save(dossier);

        return mapToDTO(dossier, new DossierDTO());

    }

    public MediaType determineFileMediaType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        }
        // Par défaut, renvoyer IMAGE_JPEG si le type n'est pas reconnu
        return MediaType.IMAGE_JPEG;
    }

    public DossierDTO get(final Long id) {
        return dossierRepository.findById(id)
                .map(dossier -> mapToDTO(dossier, new DossierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DossierDTO dossierDTO) {
        final Dossier dossier = new Dossier();
        mapToEntity(dossierDTO, dossier);
        return dossierRepository.save(dossier).getId();
    }

    public void update(final Long id, final DossierDTO dossierDTO) {
        final Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dossierDTO, dossier);
        dossierRepository.save(dossier);
    }

    public void delete(final Long id) {
        dossierRepository.deleteById(id);
    }

    public static DossierDTO mapToDTO(final Dossier dossier, final DossierDTO dossierDTO) {
        dossierDTO.setId(dossier.getId());
        dossierDTO.setAttestationTravail(dossier.getAttestationTravail());
        dossierDTO.setAttestationCessationPaie(dossier.getAttestationCessationPaie());
        dossierDTO.setCertificatMedical(dossier.getCertificatMedical());
        dossierDTO.setDernierBulletinSalaire(dossier.getDernierBulletinSalaire());
        dossierDTO.setCopieCNI(dossier.getCopieCNI());
        return dossierDTO;
    }

    public static Dossier mapToEntity(final DossierDTO dossierDTO, final Dossier dossier) {
        dossier.setAttestationTravail(dossierDTO.getAttestationTravail());
        dossier.setAttestationCessationPaie(dossierDTO.getAttestationCessationPaie());
        dossier.setCertificatMedical(dossierDTO.getCertificatMedical());
        dossier.setDernierBulletinSalaire(dossierDTO.getDernierBulletinSalaire());
        dossier.setCopieCNI(dossierDTO.getCopieCNI());
        return dossier;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DemandeConge dossierDemandeConge = demandeCongeRepository.findFirstByDossier(dossier);
        if (dossierDemandeConge != null) {
            referencedWarning.setKey("dossier.demandeConge.dossier.referenced");
            referencedWarning.addParam(dossierDemandeConge.getId());
            return referencedWarning;
        }
        return null;
    }

}
