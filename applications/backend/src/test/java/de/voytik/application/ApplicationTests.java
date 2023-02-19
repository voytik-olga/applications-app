package de.voytik.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.voytik.application.entity.ApplicationEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.repository.ApplicationRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static de.voytik.application.entity.ApplicationEntity.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationRepository repository;

    @AfterEach
    void after() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateManualApplication() throws Exception {
        ApplicationDto request = manualApplication();
        mockMvc.perform(post("/api/property/1/applications")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        assertEquals(1, repository.count());

        ApplicationEntity application = repository.findAll().stream().findFirst().orElseThrow();
        assertEquals(request.getEmail(), application.getEmail());
        assertEquals(request.getSalutation(), application.getSalutation());
        assertEquals(request.getFirstName(), application.getFirstName());
        assertEquals(request.getLastName(), application.getLastName());
        assertEquals(request.getUserComment(), application.getUserComment());
        assertEquals(request.getCreationSource(), application.getCreationSource());
    }

    @Test
    void shouldCreatePortalApplication() throws Exception {
        ApplicationDto request = portalApplication();
        mockMvc.perform(post("/api/property/1/applications")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ApplicationEntity application = repository.findAll().stream().findFirst().orElseThrow();
        assertEquals(request.getEmail(), application.getEmail());
        assertEquals(request.getSalutation(), application.getSalutation());
        assertEquals(request.getFirstName(), application.getFirstName());
        assertEquals(request.getLastName(), application.getLastName());
        assertEquals(request.getNumberOfPerson(), application.getNumberOfPerson());
        assertEquals(request.isWbsPresent(), application.isWbsPresent());
        assertEquals(request.getEarliestMoveInDate(), application.getEarliestMoveInDate());
        assertEquals(request.isPets(), application.isPets());
        assertEquals(request.getApplicantComment(), application.getApplicantComment());
        assertEquals(request.getCreationSource(), application.getCreationSource());
    }

    @Test
    void shouldReturnApplicationById() throws Exception {
        ApplicationEntity application = mockApplication();
        repository.save(application);

        mockMvc.perform(get("/api/applications/" + application.getApplicationId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("applicationId").value(application.getApplicationId()))
                .andExpect(jsonPath("email").value(application.getEmail()))
                .andExpect(jsonPath("firstName").value(application.getFirstName()))
                .andExpect(jsonPath("lastName").value(application.getLastName()))
                .andExpect(jsonPath("salutation").value(application.getSalutation().name()))
                .andExpect(jsonPath("numberOfPerson").value(application.getNumberOfPerson()))
                .andExpect(jsonPath("wbsPresent").value(application.isWbsPresent()))
                .andExpect(jsonPath("earliestMoveInDate").value("2023-09-23"))
                .andExpect(jsonPath("pets").value(application.isPets()))
                .andExpect(jsonPath("status").value(application.getStatus().name()))
                .andExpect(jsonPath("applicantComment").value(application.getApplicantComment()))
                .andExpect(jsonPath("userComment").value(application.getUserComment()));
    }

    private ApplicationEntity mockApplication() {
        ApplicationEntity application = new ApplicationEntity();
        application.setEmail("email@email.com");
        application.setFirstName("First");
        application.setLastName("Last");
        application.setSalutation(Salutation.MR);
        application.setNumberOfPerson(1);
        application.setWbsPresent(true);
        application.setEarliestMoveInDate(LocalDate.of(2023, 9, 23));
        application.setPets(true);
        application.setStatus(Status.INVITED);
        application.setApplicantComment("Applicant comment");
        application.setUserComment("User comment");
        application.setCreationSource(CreationSource.PORTAL);
        application.setPropertyId(1);
        return application;
    }

    private ApplicationDto manualApplication() {
        return ApplicationDto.builder()
                .email("my@email.com")
                .salutation(Salutation.MR)
                .firstName("First")
                .lastName("Last")
                .userComment("User comment")
                .creationSource(CreationSource.MANUAL)
                .build();
    }

    private ApplicationDto portalApplication() {
        return ApplicationDto.builder()
                .email("my@email.com")
                .salutation(Salutation.MR)
                .firstName("First")
                .lastName("Last")
                .numberOfPerson(4)
                .wbsPresent(true)
                .earliestMoveInDate(LocalDate.of(2023, 2, 23))
                .pets(true)
                .applicantComment("Applicant comment")
                .creationSource(CreationSource.PORTAL)
                .build();
    }
}