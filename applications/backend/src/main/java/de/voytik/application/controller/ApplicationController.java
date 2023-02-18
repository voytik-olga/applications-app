package de.voytik.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.service.ApplicationService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/property/{propertyId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public void createApplication(@PathVariable long propertyId, @Valid @RequestBody ApplicationDto applicationDto) {
        log.info("Incoming request to create a new property application. [propertyId: {}, application: {}]",
                propertyId, applicationDto);
        applicationService.createApplication(propertyId, applicationDto);
    }

    @GetMapping("/applications/{applicationId}")
    public ApplicationDto getApplication(@PathVariable long applicationId) {
        log.info("Incoming request to get a property application. [applicationId: {}]", applicationId);
        return applicationService.getApplication(applicationId);
    }

}
