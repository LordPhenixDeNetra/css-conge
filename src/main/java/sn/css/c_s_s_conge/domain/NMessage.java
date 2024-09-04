package sn.css.c_s_s_conge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

/**
 * Entité représentant un message dans le système.
 * <p>
 * Cette entité stocke les informations relatives aux messages, y compris les détails du message,
 * l'expéditeur, le destinataire, ainsi que les dates de création et de dernière mise à jour.
 * </p>
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "n_message")
public class NMessage {

    /**
     * Identifiant unique du message.
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
     * Contenu du message.
     */
    private String message;

    /**
     * Référencement au salarié destinataire du message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Salarier salarier;

    /**
     * Référencement à l'utilisateur expéditeur du message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private NUser user;

    /**
     * Date de création du message.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    /**
     * Date de dernière mise à jour du message.
     */
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;
}
