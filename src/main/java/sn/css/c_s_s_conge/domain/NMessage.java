package sn.css.c_s_s_conge.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class NMessage {
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

    private String message;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salarier_id")
    private Salarier salarier;

    //Valide
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime dateCreated;

    //Valide
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime lastUpdated;
}
