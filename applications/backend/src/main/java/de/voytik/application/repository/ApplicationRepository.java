package de.voytik.application.repository;

import de.voytik.application.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

}

