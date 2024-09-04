package sn.css.c_s_s_conge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.css.c_s_s_conge.model.DemandeStatus;

/**
 * Représente une demande de congé dans l'application.
 * Cette classe est une entité JPA et sera stockée dans une base de données relationnelle.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DemandeConge {

    /**
     * Identifiant unique de la demande de congé.
     */
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

    /**
     * Statut de la demande de congé.
     * @see DemandeStatus
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DemandeStatus status;

    /**
     * Salarié à qui appartient la demande de congé.
     * La relation est chargée de manière différée (lazy loading).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salarier_id")
    private Salarier salarier;

    /**
     * Dossier lié à la demande de congé.
     * La relation est chargée de manière différée (lazy loading).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id")
    private Dossier dossier;

    /**
     * Date de création de la demande de congé.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    /**
     * Date de dernière modification de la demande de congé.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
