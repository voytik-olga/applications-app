package de.voytik.application.service;

import de.voytik.application.entity.ApplicationEntity;
import de.voytik.application.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.repository.ApplicationRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationMapper mapper;
    private final ApplicationRepository repository;

    public void createApplication(long propertyId, ApplicationDto applicationDto) {
       ApplicationEntity applicationEntity = mapper.applicationDtoToEntity(applicationDto);
       applicationEntity.setPropertyId(propertyId);
       applicationEntity.setStatus(ApplicationEntity.Status.CREATED);
       repository.save(applicationEntity);
    }

    public ApplicationDto getApplication(long applicationId) {
        ApplicationEntity applicationEntity = repository.findById(applicationId).orElseThrow(
                () -> new RuntimeException("Application not found."));
        return mapper.applicationEntityToDto(applicationEntity);
    }
}
