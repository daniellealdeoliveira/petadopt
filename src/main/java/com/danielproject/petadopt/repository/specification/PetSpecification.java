package com.danielproject.petadopt.repository.specification;

import com.danielproject.petadopt.dto.PetRequest;
import com.danielproject.petadopt.model.Pet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PetSpecification implements Specification<Pet> {

    private static final long serialVersionUID = 1L;
    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String CREATED_DATE = "createdDate";
    private static final String STATUS = "status";
    private static final String CATEGORY = "category";


    private final PetRequest request;

    public PetSpecification(PetRequest request) {
        super();
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (request.getTerm() != null) {
            Predicate likeDescription = criteriaBuilder.like(criteriaBuilder.upper(root.get(DESCRIPTION)), "%" +
                    request.getTerm().toUpperCase() + "%");
            Predicate likeName = criteriaBuilder.like(criteriaBuilder.upper(root.get(NAME)), "%" +
                    request.getTerm().toUpperCase() + "%");
            predicates
                    .add(criteriaBuilder.or(likeDescription, likeName));
        }

        if (request.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(root.get(CATEGORY), request.getCategory()));
        }

        if (request.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get(STATUS), request.getStatus()));
        }

        if (request.getCreatedDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get(CREATED_DATE), request.getCreatedDate()));
        }

        query.orderBy(criteriaBuilder.asc(root.get(DESCRIPTION)));
        query.distinct(true);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
