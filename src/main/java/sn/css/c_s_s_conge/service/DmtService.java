package sn.css.c_s_s_conge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.css.c_s_s_conge.domain.Dmt;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.model.DossierDTO;
import sn.css.c_s_s_conge.repos.DmtRepository;
import sn.css.c_s_s_conge.util.NotFoundException;
import sn.css.c_s_s_conge.util.ReferencedWarning;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DmtService {

    private final DmtRepository dmtRepository;

    // Répertoire de téléchargement des images, défini dans application.properties
    @Value("${file.upload.dmt}")
    private String fileUploadDmt;

    public DmtService(final DmtRepository dmtRepository) {
        this.dmtRepository = dmtRepository;
    }

    public List<DmtDTO> findAll() {
        final List<Dmt> dmts = dmtRepository.findAll(Sort.by("id"));
        return dmts.stream()
            .map(dmt -> mapToDTO(dmt, new DmtDTO()))
            .toList();
    }

    public DmtDTO get(final Long id) {
        return dmtRepository.findById(id)
            .map(dmt -> mapToDTO(dmt, new DmtDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Long create(final DmtDTO dmtDTO) {
        final Dmt dmt = new Dmt();
        mapToEntity(dmtDTO, dmt);
        return dmtRepository.save(dmt).getId();
    }

    public Long createWithFile(final DmtDTO dmtDTO, MultipartFile file) {

        final Dmt dmt = new Dmt();
        String fileName = "";
        String contentType = file.getContentType();

        // Vérification du type de contenu du fichier
        if (contentType == null || (!contentType.equals("image/png") &&
            !contentType.equals("image/jpeg") &&
            !contentType.equals("image/jpg") &&
            !contentType.equals("image/gif")&&
            !contentType.equals("application/pdf"))) {
            // Si le type de contenu n'est pas supporté, une exception est levée on ne fait rien

        }else {
            fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            try {
                // Enregistrez le fichier dans le dossier "images"
                Path imagePath = Path.of(fileUploadDmt + fileName);
                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
            }
        }

        dmtDTO.setDocument(fileName);

        mapToEntity(dmtDTO, dmt);
        return dmtRepository.save(dmt).getId();
    }

    public void update(final Long id, final DmtDTO dmtDTO) {
        final Dmt dmt = dmtRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        mapToEntity(dmtDTO, dmt);
        dmtRepository.save(dmt);
    }

    public void delete(final Long id) {
        dmtRepository.deleteById(id);
    }

    private DmtDTO mapToDTO(final Dmt dmt, final DmtDTO dmtDTO) {

        dmtDTO.setId(dmt.getId());
        dmtDTO.setNumArticleL143(dmt.getNumArticleL143());
        dmtDTO.setNin(dmt.getNin());
        dmtDTO.setPrenom(dmt.getPrenom());
        dmtDTO.setNom(dmt.getNom());
        dmtDTO.setDateNaissane(dmt.getDateNaissane());
        dmtDTO.setLieuNaissance(dmt.getLieuNaissance());
        dmtDTO.setAdresse(dmt.getAdresse());
        dmtDTO.setEmail(dmt.getEmail());
        dmtDTO.setTelephone1(dmt.getTelephone1());
        dmtDTO.setDocument(dmt.getDocument());
//        DmtDTO.setSite(Dmt.getSite() == null ? null : Dmt.getSite().getId());
        return dmtDTO;
    }

    private Dmt mapToEntity(final DmtDTO dmtDTO, final Dmt dmt) {

        dmt.setNumArticleL143(dmtDTO.getNumArticleL143());
        dmt.setNin(dmtDTO.getNin());
        dmt.setPrenom(dmtDTO.getPrenom());
        dmt.setNom(dmtDTO.getNom());
        dmt.setDateNaissane(dmtDTO.getDateNaissane());
        dmt.setLieuNaissance(dmtDTO.getLieuNaissance());
        dmt.setAdresse(dmtDTO.getAdresse());
        dmt.setEmail(dmtDTO.getEmail());
        dmt.setTelephone1(dmtDTO.getTelephone1());
        dmt.setDocument(dmtDTO.getDocument());
//        final Site site = DmtDTO.getSite() == null ? null : siteRepository.findById(DmtDTO.getSite())
//            .orElseThrow(() -> new NotFoundException("site not found"));
//        Dmt.setSite(site);
        return dmt;
    }


    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();

        final Dmt dmt = dmtRepository.findById(id)
            .orElseThrow(NotFoundException::new);
//        if (DmtDemandeConge != null) {
//            referencedWarning.setKey("Dmt.demandeConge.Dmt.referenced");
//            referencedWarning.addParam(DmtDemandeConge.getId());
//            return referencedWarning;
//        }
        return null;
    }

}
