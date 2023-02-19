package de.voytik.application.repository;

import de.voytik.application.entity.ApplicationEntity;
import de.voytik.application.entity.ApplicationEntity_;
import de.voytik.application.search.SearchParam;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {

    private ApplicationSpecification() {
    }

    public static Specification<ApplicationEntity> filterApplications(List<SearchParam> searchParams) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            searchParams.forEach(param -> predicates.add(criteriaBuilder.equal(root.get(param.getName()), param.getValue())));
            query.orderBy(criteriaBuilder.desc(root.get(ApplicationEntity_.CREATION_TIMESTAMP)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}