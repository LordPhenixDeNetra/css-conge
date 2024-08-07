package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandeCongeResponseDTO {

    private Long id;

    @NotNull
    private DemandeStatus status;

    private Long salarier;

    private DossierDTO dossierDTO;
}
