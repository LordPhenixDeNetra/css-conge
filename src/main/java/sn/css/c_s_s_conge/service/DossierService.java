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

/**
 * Service pour gérer les opérations liées aux dossiers.
 * <p>
 * Fournit des méthodes pour créer, lire, mettre à jour et supprimer des dossiers.
 * </p>
 */
@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final DemandeCongeRepository demandeCongeRepository;

    // Répertoire de téléchargement des images, défini dans application.properties
    @Value("${file.upload.dir}")
    private String fileUploadDir;

    /**
     * Constructeur pour le service {@link DossierService}.
     *
     * @param dossierRepository Repository pour les opérations sur les dossiers.
     * @param demandeCongeRepository Repository pour les opérations sur les demandes de congé.
     */
    public DossierService(final DossierRepository dossierRepository,
            final DemandeCongeRepository demandeCongeRepository) {
        this.dossierRepository = dossierRepository;
        this.demandeCongeRepository = demandeCongeRepository;
    }

    /**
     * Trouve tous les dossiers.
     *
     * @return Liste des DTO représentant tous les dossiers.
     */
    public List<DossierDTO> findAll() {
        final List<Dossier> dossiers = dossierRepository.findAll(Sort.by("id"));
        return dossiers.stream()
                .map(dossier -> mapToDTO(dossier, new DossierDTO()))
                .toList();
    }

    // Méthode pour ajouter plusieurs fichiers
    /**
     * Ajoute plusieurs fichiers à un dossier.
     *
     * @param files Liste de fichiers à ajouter.
     * @return DTO représentant le dossier avec les fichiers ajoutés.
     */
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

    /**
     * Détermine le type de média d'un fichier.
     *
     * @param fileName Nom du fichier.
     * @return Type de média du fichier.
     */
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

    /**
     * Trouve un dossier par son identifiant.
     *
     * @param id Identifiant du dossier.
     * @return DTO représentant le dossier.
     * @throws NotFoundException Si le dossier n'est pas trouvé.
     */
    public DossierDTO get(final Long id) {
        return dossierRepository.findById(id)
                .map(dossier -> mapToDTO(dossier, new DossierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Crée un nouveau dossier.
     *
     * @param dossierDTO DTO représentant le dossier à créer.
     * @return Identifiant du dossier créé.
     */
    public Long create(final DossierDTO dossierDTO) {
        final Dossier dossier = new Dossier();
        mapToEntity(dossierDTO, dossier);
        return dossierRepository.save(dossier).getId();
    }

    /**
     * Met à jour un dossier existant.
     *
     * @param id Identifiant du dossier à mettre à jour.
     * @param dossierDTO DTO représentant les nouvelles données du dossier.
     * @throws NotFoundException Si le dossier n'est pas trouvé.
     */
    public void update(final Long id, final DossierDTO dossierDTO) {
        final Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dossierDTO, dossier);
        dossierRepository.save(dossier);
    }

    /**
     * Supprime un dossier par son identifiant.
     *
     * @param id Identifiant du dossier à supprimer.
     */
    public void delete(final Long id) {
        dossierRepository.deleteById(id);
    }

    /**
     * Convertit un objet {@link Dossier} en {@link DossierDTO}.
     *
     * @param dossier Entité à convertir.
     * @param dossierDTO DTO à remplir.
     * @return DTO rempli avec les données de l'entité.
     */
    public static DossierDTO mapToDTO(final Dossier dossier, final DossierDTO dossierDTO) {
        dossierDTO.setId(dossier.getId());
        dossierDTO.setAttestationTravail(dossier.getAttestationTravail());
        dossierDTO.setAttestationCessationPaie(dossier.getAttestationCessationPaie());
        dossierDTO.setCertificatMedical(dossier.getCertificatMedical());
        dossierDTO.setDernierBulletinSalaire(dossier.getDernierBulletinSalaire());
        dossierDTO.setCopieCNI(dossier.getCopieCNI());
        return dossierDTO;
    }

    /**
     * Convertit un {@link DossierDTO} en entité {@link Dossier}.
     *
     * @param dossierDTO DTO à convertir.
     * @param dossier Entité à remplir.
     * @return Entité remplie avec les données du DTO.
     */
    public static Dossier mapToEntity(final DossierDTO dossierDTO, final Dossier dossier) {
        dossier.setAttestationTravail(dossierDTO.getAttestationTravail());
        dossier.setAttestationCessationPaie(dossierDTO.getAttestationCessationPaie());
        dossier.setCertificatMedical(dossierDTO.getCertificatMedical());
        dossier.setDernierBulletinSalaire(dossierDTO.getDernierBulletinSalaire());
        dossier.setCopieCNI(dossierDTO.getCopieCNI());
        return dossier;
    }

    /**
     * Vérifie s'il y a un avertissement de référence pour un dossier donné.
     *
     * @param id Identifiant du dossier.
     * @return Avertissement de référence si un avertissement existe, sinon {@code null}.
     * @throws NotFoundException Si le dossier n'est pas trouvé.
     */
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
