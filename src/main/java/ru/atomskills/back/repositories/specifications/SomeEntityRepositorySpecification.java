package ru.atomskills.back.repositories.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import ru.atomskills.back.models.SomeEntity;
import ru.atomskills.back.models.SomeEntity_;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class SomeEntityRepositorySpecification {
    public static Specification<SomeEntity> filter(String textQuery,
                                                   Integer fromNumber, Integer toNumber,
                                                   LocalDateTime fromDate, LocalDateTime toDate,
                                                   List<SomeEntity.SomeEnum> enums) {
        return (Root<SomeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(textQuery)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("text")), "%" + textQuery.toLowerCase() +"%"));
            }

            if (nonNull(fromNumber)) {
                predicates.add(criteriaBuilder.ge(root.get(SomeEntity_.number), fromNumber));
            }

            if (nonNull(toNumber)) {
                predicates.add(criteriaBuilder.le(root.get(SomeEntity_.number), toNumber));
            }

            if (nonNull(fromDate)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(SomeEntity_.date), fromDate));
            }

            if (nonNull(toDate)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(SomeEntity_.date), toDate));
            }

            if (!CollectionUtils.isEmpty(enums)) {
                predicates.add(root.get(SomeEntity_.someEnum).in(enums));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
