package sn.css.c_s_s_conge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.css.c_s_s_conge.domain.*;
import sn.css.c_s_s_conge.domain.NMessage;
import sn.css.c_s_s_conge.model.DemandeCongeDTO;
import sn.css.c_s_s_conge.model.DmtDTO;
import sn.css.c_s_s_conge.model.NMessageDTO;
import sn.css.c_s_s_conge.repos.DemandeCongeRepository;
import sn.css.c_s_s_conge.repos.NMessageRepository;
import sn.css.c_s_s_conge.repos.NMessageRepository;
import sn.css.c_s_s_conge.repos.SalarierRepository;
import sn.css.c_s_s_conge.util.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NMessageService {

    private final NMessageRepository messageRepository;
    private final SalarierRepository salarierRepository;

    public Long create(final NMessageDTO messageDTO) {
        final NMessage message = new NMessage();
        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getId();
    }

    public NMessageDTO get(final Long id) {
        return messageRepository.findById(id)
            .map(message -> mapToDTO(message, new NMessageDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public NMessageDTO getMessageBySalarierId(Long salarierId){
        Salarier salarier = salarierRepository.findById(salarierId).get();
        if (salarier != null){
            NMessage message = messageRepository.findNMessageBySalarier(salarier);
            return mapToDTO(message, new NMessageDTO());
        }
        return null;
    }

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


    public NMessageDTO mapToDTO(final NMessage nMessage, final NMessageDTO nMessageDTO) {
        nMessageDTO.setId(nMessage.getId());
        nMessageDTO.setMessage(nMessage.getMessage());
        nMessageDTO.setSalarier(nMessage.getSalarier() == null ? null : nMessage.getSalarier().getId());
        return nMessageDTO;
    }

    public NMessage mapToEntity(final NMessageDTO nMessageDTO, final NMessage nMessage) {

        nMessage.setMessage(nMessageDTO.getMessage());
        final Salarier salarier = nMessageDTO.getSalarier() == null ? null : salarierRepository.findById(nMessageDTO.getSalarier())
            .orElseThrow(() -> new NotFoundException("salarier not found"));
        nMessage.setSalarier(salarier);

        return nMessage;
    }
}
