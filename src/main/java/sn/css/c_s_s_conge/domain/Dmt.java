package sn.css.c_s_s_conge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "n_dmt")
public class Dmt {

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

    @Column(nullable = false, name = "n_dmt_numero_allocataire")
    private String numArticleL143;

    @Column(nullable = false, name = "n_dmt_nin")
    private String nin;

    @Column
    private String prenom;

    @Column
    private String nom;

    @Column(name = "n_dmt_date_naissance")
    private LocalDate dateNaissane;

    @Column(name = "n_dmt_lieu_naissance")
    private String lieuNaissance;

    @Column(name = "n_dmt_adresse_ville")
    private String adresse;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "n_dmt_telephone")
    private String telephone1;

    @Column(nullable = false, name = "n_dmt_document")
    private String document;

    //Valide
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    //Valide
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;

}
