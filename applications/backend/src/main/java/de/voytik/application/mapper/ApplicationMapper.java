package de.voytik.application.mapper;

import org.mapstruct.Mapper;
import de.voytik.application.dto.ApplicationDto;
import de.voytik.application.entity.ApplicationEntity;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    ApplicationEntity applicationDtoToEntity(ApplicationDto applicationDto);
    ApplicationDto applicationEntityToDto(ApplicationEntity applicationEntity);
}
