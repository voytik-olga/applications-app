package de.voytik.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "application")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long applicationId;

    @Column(nullable = false)
    private String email;

    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    private int numberOfPerson;

    private boolean wbsPresent;

    private LocalDate earliestMoveInDate;

    private boolean pets;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String applicantComment;

    private String userComment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreationSource creationSource;

    private long propertyId;

    @CreatedDate
    private LocalDateTime creationTimestamp;

    @LastModifiedDate
    private LocalDateTime modificationTimestamp;

    public enum CreationSource {
        MANUAL, PORTAL
    }

    public enum Salutation {
        MR, MRS
    }

    public enum Status {
        CREATED, INVITED, DECLINED
    }

}
