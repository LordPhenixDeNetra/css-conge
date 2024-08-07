package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SalarierDTO {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String numArticleL143;

    @NotNull
    @Size(max = 25)
    @SalarierNinUnique
    private String nin;

    @Size(max = 100)
    private String prenom;

    @Size(max = 25)
    private String nom;

    private LocalDate dateNaissane;

    @Size(max = 255)
    private String lieuNaissance;

    @Size(max = 255)
    private String nomMereComplet;

    @Size(max = 25)
    private String prenomPere;

    @Size(max = 255)
    private String adresse;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 100)
    private String telephone1;

    @Size(max = 100)
    private String telephone2;

    @Size(max = 255)
    private String compteBancaire;

    @NotNull
    private LocalDate dateEmbauche;

    @NotNull
    private Long salaire;

    @NotNull
    private LocalDate debutConge;

    private Long site;

}
