package sn.css.c_s_s_conge.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "allocataires")
public class Salarier {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, name = "numero_allocataire")
    private String numArticleL143;

    @Column(nullable = false, name = "numero_identification_nationale")
    private String nin;

    @Column
    private String prenom;

    @Column
    private String nom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissane;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

//    @Column
//    private String nomMereComplet;

//    @Column
//    private String prenomPere;

    @Column(name = "adresse_ville")
    private String adresse;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "telephone")
    private String telephone1;

//    @Column(length = 100)
//    private String telephone2;

//    @Column
//    private String compteBancaire;
//
//    @Column(nullable = false)
//    private LocalDate dateEmbauche;

//    @Column(nullable = false)
//    private Long salaire;

//    @Column(nullable = false)
//    private LocalDate debutConge;

    @OneToMany(mappedBy = "salarier")
    private Set<DemandeConge> demandeConges;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "site_id")
//    private Site site;

    //Valide
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    //Valide
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;

}
