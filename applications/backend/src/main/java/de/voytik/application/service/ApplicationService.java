package de.voytik.application.service;

import de.voytik.application.dto.ApplicationDto;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    void createApplication(long propertyId, ApplicationDto applicationDto);
    ApplicationDto getApplication(long applicationId);
    List<ApplicationDto> searchApplications(long propertyId, Map<String, String> searchParams);
}
