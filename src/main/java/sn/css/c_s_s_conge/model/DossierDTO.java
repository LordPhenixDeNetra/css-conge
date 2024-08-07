package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DossierDTO {

    private Long id;

    @NotNull
    private String attestationTravail;

    @NotNull
    private String attestationCessationPaie;

    @NotNull
    private String certificatMedical;

    @NotNull
    private String dernierBulletinSalaire;

    @NotNull
    private String copieCNI;

}
