package de.voytik.application.controller;

import de.voytik.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.voytik.application.dto.ApplicationDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/property/{propertyId}/applications")
    public List<ApplicationDto> getAllApplications(@PathVariable long propertyId,
                                                   @RequestParam Map<String,String> searchParams) {
        log.info("Incoming request to get all property applications. [propertyId: {}, searchParams: {}]",
                propertyId, searchParams);
        List<ApplicationDto> applications = applicationService.searchApplications(propertyId, searchParams);
        log.info("{} applications were found.", applications.size());
        return applications;
    }

    @GetMapping("/applications/{applicationId}")
    public ApplicationDto getApplication(@PathVariable long applicationId) {
        log.info("Incoming request to get a property application. [applicationId: {}]", applicationId);
        ApplicationDto application =  applicationService.getApplication(applicationId);
        log.info("An application was found: {}", application);
        return applicationService.getApplication(applicationId);
    }

}
