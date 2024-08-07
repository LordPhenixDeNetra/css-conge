package sn.css.c_s_s_conge.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.domain.Site;
import sn.css.c_s_s_conge.model.SalarierDTO;
import sn.css.c_s_s_conge.repos.DemandeCongeRepository;
import sn.css.c_s_s_conge.repos.SalarierRepository;
import sn.css.c_s_s_conge.repos.SiteRepository;
import sn.css.c_s_s_conge.util.NotFoundException;
import sn.css.c_s_s_conge.util.ReferencedWarning;


@Service
public class SalarierService {

    private final SalarierRepository salarierRepository;
    private final SiteRepository siteRepository;
    private final DemandeCongeRepository demandeCongeRepository;

    public SalarierService(final SalarierRepository salarierRepository,
            final SiteRepository siteRepository,
            final DemandeCongeRepository demandeCongeRepository) {
        this.salarierRepository = salarierRepository;
        this.siteRepository = siteRepository;
        this.demandeCongeRepository = demandeCongeRepository;
    }

    public List<SalarierDTO> findAll() {
        final List<Salarier> salariers = salarierRepository.findAll(Sort.by("id"));
        return salariers.stream()
                .map(salarier -> mapToDTO(salarier, new SalarierDTO()))
                .toList();
    }

    public SalarierDTO findSalarierByNin(final String nin) {

        Salarier salarier = salarierRepository.findSalarierByNin(nin);

        return mapToDTO(salarier, new SalarierDTO());
    }

    public SalarierDTO get(final Long id) {
        return salarierRepository.findById(id)
                .map(salarier -> mapToDTO(salarier, new SalarierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SalarierDTO salarierDTO) {
        final Salarier salarier = new Salarier();
        mapToEntity(salarierDTO, salarier);
        return salarierRepository.save(salarier).getId();
    }

    public void update(final Long id, final SalarierDTO salarierDTO) {
        final Salarier salarier = salarierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(salarierDTO, salarier);
        salarierRepository.save(salarier);
    }

    public void delete(final Long id) {
        salarierRepository.deleteById(id);
    }

    private SalarierDTO mapToDTO(final Salarier salarier, final SalarierDTO salarierDTO) {
        salarierDTO.setId(salarier.getId());
        salarierDTO.setNumArticleL143(salarier.getNumArticleL143());
        salarierDTO.setNin(salarier.getNin());
        salarierDTO.setPrenom(salarier.getPrenom());
        salarierDTO.setNom(salarier.getNom());
        salarierDTO.setDateNaissane(salarier.getDateNaissane());
        salarierDTO.setLieuNaissance(salarier.getLieuNaissance());
//        salarierDTO.setNomMereComplet(salarier.getNomMereComplet());
//        salarierDTO.setPrenomPere(salarier.getPrenomPere());
        salarierDTO.setAdresse(salarier.getAdresse());
        salarierDTO.setEmail(salarier.getEmail());
        salarierDTO.setTelephone1(salarier.getTelephone1());
//        salarierDTO.setTelephone2(salarier.getTelephone2());
//        salarierDTO.setCompteBancaire(salarier.getCompteBancaire());
//        salarierDTO.setDateEmbauche(salarier.getDateEmbauche());
//        salarierDTO.setSalaire(salarier.getSalaire());
//        salarierDTO.setDebutConge(salarier.getDebutConge());
//        salarierDTO.setSite(salarier.getSite() == null ? null : salarier.getSite().getId());
        return salarierDTO;
    }

    private Salarier mapToEntity(final SalarierDTO salarierDTO, final Salarier salarier) {
        salarier.setNumArticleL143(salarierDTO.getNumArticleL143());
        salarier.setNin(salarierDTO.getNin());
        salarier.setPrenom(salarierDTO.getPrenom());
        salarier.setNom(salarierDTO.getNom());
        salarier.setDateNaissane(salarierDTO.getDateNaissane());
        salarier.setLieuNaissance(salarierDTO.getLieuNaissance());
//        salarier.setNomMereComplet(salarierDTO.getNomMereComplet());
//        salarier.setPrenomPere(salarierDTO.getPrenomPere());
        salarier.setAdresse(salarierDTO.getAdresse());
        salarier.setEmail(salarierDTO.getEmail());
        salarier.setTelephone1(salarierDTO.getTelephone1());
//        salarier.setTelephone2(salarierDTO.getTelephone2());
//        salarier.setCompteBancaire(salarierDTO.getCompteBancaire());
//        salarier.setDateEmbauche(salarierDTO.getDateEmbauche());
//        salarier.setSalaire(salarierDTO.getSalaire());
//        salarier.setDebutConge(salarierDTO.getDebutConge());
        final Site site = salarierDTO.getSite() == null ? null : siteRepository.findById(salarierDTO.getSite())
                .orElseThrow(() -> new NotFoundException("site not found"));
//        salarier.setSite(site);
        return salarier;
    }

    public boolean ninExists(final String nin) {
        return salarierRepository.existsByNinIgnoreCase(nin);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Salarier salarier = salarierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DemandeConge salarierDemandeConge = demandeCongeRepository.findFirstBySalarier(salarier);
        if (salarierDemandeConge != null) {
            referencedWarning.setKey("salarier.demandeConge.salarier.referenced");
            referencedWarning.addParam(salarierDemandeConge.getId());
            return referencedWarning;
        }
        return null;
    }

}
