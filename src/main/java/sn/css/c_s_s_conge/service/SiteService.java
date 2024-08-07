package sn.css.c_s_s_conge.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.domain.Site;
import sn.css.c_s_s_conge.model.SiteDTO;
import sn.css.c_s_s_conge.repos.SalarierRepository;
import sn.css.c_s_s_conge.repos.SiteRepository;
import sn.css.c_s_s_conge.util.NotFoundException;
import sn.css.c_s_s_conge.util.ReferencedWarning;


@Service
public class SiteService {

    private final SiteRepository siteRepository;
    private final SalarierRepository salarierRepository;

    public SiteService(final SiteRepository siteRepository,
            final SalarierRepository salarierRepository) {
        this.siteRepository = siteRepository;
        this.salarierRepository = salarierRepository;
    }

    public List<SiteDTO> findAll() {
        final List<Site> sites = siteRepository.findAll(Sort.by("id"));
        return sites.stream()
                .map(site -> mapToDTO(site, new SiteDTO()))
                .toList();
    }

    public SiteDTO get(final Long id) {
        return siteRepository.findById(id)
                .map(site -> mapToDTO(site, new SiteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SiteDTO siteDTO) {
        final Site site = new Site();
        mapToEntity(siteDTO, site);
        return siteRepository.save(site).getId();
    }

    public void update(final Long id, final SiteDTO siteDTO) {
        final Site site = siteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(siteDTO, site);
        siteRepository.save(site);
    }

    public void delete(final Long id) {
        siteRepository.deleteById(id);
    }

    private SiteDTO mapToDTO(final Site site, final SiteDTO siteDTO) {
        siteDTO.setId(site.getId());
        siteDTO.setNumSite(site.getNumSite());
        siteDTO.setNomSite(site.getNomSite());
        return siteDTO;
    }

    private Site mapToEntity(final SiteDTO siteDTO, final Site site) {
        site.setNumSite(siteDTO.getNumSite());
        site.setNomSite(siteDTO.getNomSite());
        return site;
    }

//    public ReferencedWarning getReferencedWarning(final Long id) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final Site site = siteRepository.findById(id)
//                .orElseThrow(NotFoundException::new);
//        final Salarier siteSalarier = salarierRepository.findFirstBySite(site);
//        if (siteSalarier != null) {
//            referencedWarning.setKey("site.salarier.site.referenced");
//            referencedWarning.addParam(siteSalarier.getId());
//            return referencedWarning;
//        }
//        return null;
//    }

}
