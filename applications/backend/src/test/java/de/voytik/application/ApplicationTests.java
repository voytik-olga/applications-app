package de.voytik.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.repository.ApplicationRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/api/property/1/applications")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(manualApplication())))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, repository.count());
    }

    @Test
    void shouldCreatePortalApplication() throws Exception {
        mockMvc.perform(post("/api/property/1/applications")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(portalApplication())))
                .andExpect(status().isCreated());
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