package de.voytik.application.service;

import de.voytik.application.entity.ApplicationEntity;
import de.voytik.application.entity.ApplicationEntity.Status;
import de.voytik.application.mapper.ApplicationMapper;
import de.voytik.application.repository.ApplicationSpecification;
import de.voytik.application.search.SearchParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.repository.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper mapper;
    private final ApplicationRepository repository;

    public void createApplication(long propertyId, ApplicationDto applicationDto) {
        ApplicationEntity applicationEntity = mapper.applicationDtoToEntity(applicationDto);
        applicationEntity.setPropertyId(propertyId);
        applicationEntity.setStatus(Status.CREATED);
        repository.save(applicationEntity);
    }

    public ApplicationDto getApplication(long applicationId) {
        ApplicationEntity applicationEntity = repository.findById(applicationId).orElseThrow(
                () -> new RuntimeException("application not found"));
        return mapper.applicationEntityToDto(applicationEntity);
    }

    public List<ApplicationDto> searchApplications(long propertyId, Map<String, String> searchParams) {
        List<SearchParam> normalizedParams = normalizeParams(propertyId, searchParams);
        List<ApplicationEntity> entities = repository.findAll(
                ApplicationSpecification.filterApplications(normalizedParams));
        return mapper.applicationEntityListToDtoList(entities);
    }

    private static List<SearchParam> normalizeParams(long propertyId, Map<String, String> searchParams) {
        List<SearchParam> filteredParams = new ArrayList<>();
        filteredParams.add(new SearchParam("propertyId", propertyId));
        searchParams.forEach((key, value) -> {
            switch (key) {
                case "status":
                    filteredParams.add(new SearchParam(key, Status.valueOf(value)));
                    break;
                case "numberOfPerson":
                    filteredParams.add(new SearchParam(key, Integer.valueOf(value)));
                    break;
                case "wbsPresent":
                    filteredParams.add(new SearchParam(key, Boolean.valueOf(value)));
                    break;
                case "email":
                    filteredParams.add(new SearchParam(key, value));
                    break;
                default:
                    log.warn("unknown search field");
            }
        });
       return filteredParams;
    }
    
}
