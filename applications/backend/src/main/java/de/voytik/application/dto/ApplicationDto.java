package de.voytik.application.dto;

import de.voytik.application.entity.ApplicationEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ApplicationDto {

    private long applicationId;

    @Email
    @NotBlank
    private String email;
    private String firstName;

    @NotBlank
    private String lastName;

    private ApplicationEntity.Salutation salutation;
    private int numberOfPerson;
    private boolean wbsPresent;
    private LocalDate earliestMoveInDate;
    private boolean pets;
    private ApplicationEntity.Status status;
    private String applicantComment;
    private String userComment;

    @NotNull
    private ApplicationEntity.CreationSource creationSource;

}
