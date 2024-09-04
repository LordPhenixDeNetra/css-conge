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

    /**
     * Constructeur pour injecter les dépendances nécessaires.
     * @param salarierRepository Repository pour les entités `Salarier`.
     * @param siteRepository Repository pour les entités `Site`.
     * @param demandeCongeRepository Repository pour les entités `DemandeConge`.
     */
    public SalarierService(final SalarierRepository salarierRepository,
            final SiteRepository siteRepository,
            final DemandeCongeRepository demandeCongeRepository) {
        this.salarierRepository = salarierRepository;
        this.siteRepository = siteRepository;
        this.demandeCongeRepository = demandeCongeRepository;
    }

    /**
     * Récupère tous les salariés triés par identifiant.
     * @return Liste de DTOs des salariés.
     */
    public List<SalarierDTO> findAll() {
        final List<Salarier> salariers = salarierRepository.findAll(Sort.by("id"));
        return salariers.stream()
                .map(salarier -> mapToDTO(salarier, new SalarierDTO()))
                .toList();
    }

    /**
     * Récupère un salarié par son NIN (Numéro d'Identification National).
     * @param nin Numéro d'identification national.
     * @return DTO du salarié.
     */
    public SalarierDTO findSalarierByNin(final String nin) {

        Salarier salarier = salarierRepository.findSalarierByNin(nin);

        return mapToDTO(salarier, new SalarierDTO());
    }

    /**
     * Récupère un salarié par son identifiant.
     * @param id Identifiant du salarié.
     * @return DTO du salarié.
     * @throws NotFoundException si le salarié n'est pas trouvé.
     */
    public SalarierDTO get(final Long id) {
        return salarierRepository.findById(id)
                .map(salarier -> mapToDTO(salarier, new SalarierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Crée un nouveau salarié.
     * @param salarierDTO DTO du salarié à créer.
     * @return Identifiant du salarié créé.
     */
    public Long create(final SalarierDTO salarierDTO) {
        final Salarier salarier = new Salarier();
        mapToEntity(salarierDTO, salarier);
        return salarierRepository.save(salarier).getId();
    }

    /**
     * Met à jour un salarié existant.
     * @param id Identifiant du salarié.
     * @param salarierDTO DTO du salarié à mettre à jour.
     * @throws NotFoundException si le salarié n'est pas trouvé.
     */
    public void update(final Long id, final SalarierDTO salarierDTO) {
        final Salarier salarier = salarierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(salarierDTO, salarier);
        salarierRepository.save(salarier);
    }

    /**
     * Supprime un salarié par son identifiant.
     * @param id Identifiant du salarié à supprimer.
     */
    public void delete(final Long id) {
        salarierRepository.deleteById(id);
    }

    /**
     * Mappe une entité `Salarier` vers un DTO `SalarierDTO`.
     * @param salarier L'entité `Salarier`.
     * @param salarierDTO Le DTO cible.
     * @return Le DTO mappé.
     */
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

    /**
     * Mappe un DTO `SalarierDTO` vers une entité `Salarier`.
     * @param salarierDTO Le DTO source.
     * @param salarier L'entité cible.
     * @return L'entité mappée.
     * @throws NotFoundException si le site associé n'est pas trouvé.
     */
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

    /**
     * Vérifie si un salarié existe en fonction de son NIN.
     * @param nin Numéro d'identification national.
     * @return true si un salarié avec le NIN donné existe, false sinon.
     */
    public boolean ninExists(final String nin) {
        return salarierRepository.existsByNinIgnoreCase(nin);
    }

    /**
     * Génère un avertissement si un salarié est référencé dans d'autres entités,
     * comme une demande de congé.
     * @param id Identifiant du salarié.
     * @return `ReferencedWarning` contenant les informations de référence, ou null si aucune référence n'est trouvée.
     * @throws NotFoundException si le salarié n'est pas trouvé.
     */
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
