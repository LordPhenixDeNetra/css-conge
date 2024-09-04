package sn.css.c_s_s_conge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.*;
import sn.css.c_s_s_conge.domain.NMessage;
import sn.css.c_s_s_conge.model.NMessageDTO;
import sn.css.c_s_s_conge.repos.*;
import sn.css.c_s_s_conge.repos.NMessageRepository;
import sn.css.c_s_s_conge.util.NotFoundException;

import java.util.List;

/**
 * Service pour la gestion des messages.
 * <p>
 * Ce service fournit des méthodes pour créer, obtenir et mapper des messages entre les entités et les DTOs.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class NMessageService {

    private final NMessageRepository messageRepository;
    private final SalarierRepository salarierRepository;
    private final NUserRepository userRepository;


    /**
     * Crée un nouveau message à partir d'un DTO.
     *
     * @param messageDTO Le DTO contenant les informations du message.
     * @return L'identifiant du message créé.
     */
    public Long create(final NMessageDTO messageDTO) {
        final NMessage message = new NMessage();
        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getId();
    }

    /**
     * Crée un nouveau message avec un expéditeur et un destinataire spécifiés.
     *
     * @param messageDTO Le DTO contenant les informations du message.
     * @param sender     Identifiant de l'expéditeur.
     * @param receiver   Identifiant du destinataire.
     * @return L'identifiant du message créé.
     */
    public Long createWithSenderAndReceiver(final NMessageDTO messageDTO, Long sender, Long receiver) {

        final NMessage message = new NMessage();

        messageDTO.setUser(sender);
        messageDTO.setSalarier(receiver);

        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getId();
    }

    /**
     * Obtient un message par son identifiant.
     *
     * @param id L'identifiant du message.
     * @return Le DTO du message.
     * @throws NotFoundException Si le message n'est pas trouvé.
     */
    public NMessageDTO get(final Long id) {
        return messageRepository.findById(id)
            .map(message -> mapToDTO(message, new NMessageDTO()))
            .orElseThrow(NotFoundException::new);
    }

    /**
     * Obtient un message basé sur l'identifiant du salarié.
     *
     * @param salarierId L'identifiant du salarié.
     * @return Le DTO du message associé au salarié ou null si non trouvé.
     */
    public NMessageDTO getMessageBySalarierId(Long salarierId){
        Salarier salarier = salarierRepository.findById(salarierId).get();
        if (salarier != null){
            NMessage message = messageRepository.findNMessageBySalarier(salarier);
            return mapToDTO(message, new NMessageDTO());
        }
        return null;
    }

    /**
     * Trouve tous les messages associés à un salarié.
     *
     * @param salarierId L'identifiant du salarié.
     * @return La liste des DTOs des messages associés au salarié.
     */
    public List<NMessageDTO> findAllMessageBySalarierId(Long salarierId) {
        Salarier salarier = salarierRepository.findById(salarierId).get();
        if (salarier != null){
            final List<NMessage> messages = messageRepository.findAllBySalarier(salarier);
            return messages.stream()
                .map(message -> mapToDTO(message, new NMessageDTO()))
                .toList();
        }
        return null;
    }


    /**
     * Mappe une entité NMessage vers un DTO NMessageDTO.
     *
     * @param nMessage     L'entité NMessage.
     * @param nMessageDTO  Le DTO NMessageDTO à remplir.
     * @return Le DTO rempli.
     */
    public NMessageDTO mapToDTO(final NMessage nMessage, final NMessageDTO nMessageDTO) {
        nMessageDTO.setId(nMessage.getId());
        nMessageDTO.setMessage(nMessage.getMessage());
        nMessageDTO.setSalarier(nMessage.getSalarier() == null ? null : nMessage.getSalarier().getId());
        nMessageDTO.setUser(nMessage.getUser() == null ? null : nMessage.getUser().getId());
        return nMessageDTO;
    }

    /**
     * Mappe un DTO NMessageDTO vers une entité NMessage.
     *
     * @param nMessageDTO  Le DTO NMessageDTO.
     * @param nMessage     L'entité NMessage à remplir.
     * @return L'entité remplie.
     * @throws NotFoundException Si le salarié ou l'utilisateur spécifié n'est pas trouvé.
     */
    public NMessage mapToEntity(final NMessageDTO nMessageDTO, final NMessage nMessage) {

        nMessage.setMessage(nMessageDTO.getMessage());
        final Salarier salarier = nMessageDTO.getSalarier() == null ? null : salarierRepository.findById(nMessageDTO.getSalarier())
            .orElseThrow(() -> new NotFoundException("salarier not found"));
        nMessage.setSalarier(salarier);

        final NUser user = nMessageDTO.getUser() == null ? null : userRepository.findById(nMessageDTO.getUser())
            .orElseThrow(() -> new NotFoundException("salarier not found"));
        nMessage.setUser(user);

        return nMessage;
    }
}
