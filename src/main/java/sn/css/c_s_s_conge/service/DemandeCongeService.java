package sn.css.c_s_s_conge.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.DemandeConge;
import sn.css.c_s_s_conge.domain.Dossier;
import sn.css.c_s_s_conge.domain.Salarier;
import sn.css.c_s_s_conge.model.DemandeCongeDTO;
import sn.css.c_s_s_conge.model.DemandeCongeResponseDTO;
import sn.css.c_s_s_conge.model.DemandeStatus;
import sn.css.c_s_s_conge.model.DossierDTO;
import sn.css.c_s_s_conge.repos.DemandeCongeRepository;
import sn.css.c_s_s_conge.repos.DossierRepository;
import sn.css.c_s_s_conge.repos.SalarierRepository;
import sn.css.c_s_s_conge.service.interfaces.EmailService;
import sn.css.c_s_s_conge.util.NotFoundException;


@Service
public class DemandeCongeService {

    private final DemandeCongeRepository demandeCongeRepository;
    private final SalarierRepository salarierRepository;
    private final DossierRepository dossierRepository;
    private final EmailService emailService;

    public DemandeCongeService(final DemandeCongeRepository demandeCongeRepository,
                               final SalarierRepository salarierRepository,
                               final DossierRepository dossierRepository, EmailService emailService) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.salarierRepository = salarierRepository;
        this.dossierRepository = dossierRepository;
        this.emailService = emailService;
    }

    public List<DemandeCongeDTO> findAll() {
        final List<DemandeConge> demandeConges = demandeCongeRepository.findAll(Sort.by("id"));
        return demandeConges.stream()
                .map(demandeConge -> mapToDTO(demandeConge, new DemandeCongeDTO()))
                .toList();
    }

//    public List<DemandeCongeResponseDTO> findAllWithDossier() {
//        final List<DemandeConge> demandeConges = demandeCongeRepository.findAll(Sort.by("id"));
//        return demandeConges.stream()
//            .map(demandeConge -> mapToDTO(demandeConge, new DemandeCongeDTO()))
//            .toList();
//    }

    public DemandeCongeDTO get(final Long id) {
        return demandeCongeRepository.findById(id)
                .map(demandeConge -> mapToDTO(demandeConge, new DemandeCongeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DemandeCongeDTO demandeCongeDTO) {
        final DemandeConge demandeConge = new DemandeConge();
        mapToEntity(demandeCongeDTO, demandeConge);
        return demandeCongeRepository.save(demandeConge).getId();
    }

    public DemandeCongeDTO createWithSalarierAndDossier(final DemandeCongeDTO demandeCongeDTO, Long salarierId, Long dossierId) {

        demandeCongeDTO.setStatus(DemandeStatus.EN_COURS_DE_TRAITEMENT);
        demandeCongeDTO.setDossier(dossierId);
        demandeCongeDTO.setSalarier(salarierId);

        DemandeConge demandeConge = demandeCongeRepository.save(mapToEntity(demandeCongeDTO, new DemandeConge()));


        emailService.sendMessage(demandeConge.getSalarier().getPrenom(), demandeConge.getSalarier().getEmail(), DemandeStatus.EN_COURS_DE_TRAITEMENT.toString().replaceAll("_", " "));

        return mapToDTO(demandeConge, new DemandeCongeDTO());

    }

    public DemandeCongeDTO findBySalarierId(Long salarierId){
        Salarier salarier = salarierRepository.findById(salarierId).get();
        DemandeConge demandeConge = demandeCongeRepository.findFirstBySalarier(salarier);

        if (demandeConge != null){
            return mapToDTO(demandeConge, new DemandeCongeDTO());
        }
        return new DemandeCongeDTO();
    }


    public void update(final Long id, final DemandeCongeDTO demandeCongeDTO) {
        final DemandeConge demandeConge = demandeCongeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(demandeCongeDTO, demandeConge);
        demandeCongeRepository.save(demandeConge);
    }

    public void delete(final Long id) {
        demandeCongeRepository.deleteById(id);
    }

    private DemandeCongeDTO mapToDTO(final DemandeConge demandeConge,
            final DemandeCongeDTO demandeCongeDTO) {
        demandeCongeDTO.setId(demandeConge.getId());
        demandeCongeDTO.setStatus(demandeConge.getStatus());
        demandeCongeDTO.setSalarier(demandeConge.getSalarier() == null ? null : demandeConge.getSalarier().getId());
        demandeCongeDTO.setDossier(demandeConge.getDossier() == null ? null : demandeConge.getDossier().getId());
        return demandeCongeDTO;
    }

    private DemandeCongeResponseDTO mapToCongeResponseDTO(final DemandeConge demandeConge,
                                     final DemandeCongeResponseDTO demandeCongeResponseDTO) {

        Dossier dossier = dossierRepository.findById(demandeConge.getDossier().getId()).get();

        DossierDTO dossierDTO =  DossierService.mapToDTO(dossier, new DossierDTO());


        demandeCongeResponseDTO.setId(demandeConge.getId());
        demandeCongeResponseDTO.setStatus(demandeConge.getStatus());
        demandeCongeResponseDTO.setDossierDTO(dossierDTO);
        demandeCongeResponseDTO.setSalarier(demandeConge.getSalarier() == null ? null : demandeConge.getSalarier().getId());
//        demandeCongeResponseDTO.setDossierDTO(demandeConge.getDossier() == null ? null : demandeConge.getDossier().getId());
        return demandeCongeResponseDTO;
    }

    private DemandeConge mapToEntity(final DemandeCongeDTO demandeCongeDTO,
            final DemandeConge demandeConge) {

        demandeConge.setStatus(demandeCongeDTO.getStatus());

        final Salarier salarier = demandeCongeDTO.getSalarier() == null ? null : salarierRepository.findById(demandeCongeDTO.getSalarier())
                .orElseThrow(() -> new NotFoundException("salarier not found"));
        demandeConge.setSalarier(salarier);

        final Dossier dossier = demandeCongeDTO.getDossier() == null ? null : dossierRepository.findById(demandeCongeDTO.getDossier())
                .orElseThrow(() -> new NotFoundException("dossier not found"));
        demandeConge.setDossier(dossier);
        return demandeConge;
    }

}
