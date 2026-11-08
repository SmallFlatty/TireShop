package com.tirestore.tireshop.specification;

import com.tirestore.tireshop.dto.request.TireFilterRequest;
import com.tirestore.tireshop.entity.Tire;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TireSpecifications {

    public static Specification<Tire> getTireSpecification(TireFilterRequest request) {
        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();

            if(request.width() != null)
                predicates.add(cb.equal(root.get("width"),request.width()));

            if(request.profile() != null)
                predicates.add(cb.equal(root.get("profile"),request.profile()));

            if(request.speedRating() != null)
                predicates.add(cb.equal(root.get("speedRating"),request.speedRating()));

            if(request.diameter() != null)
                predicates.add(cb.equal(root.get("diameter"),request.diameter()));

            if(request.seasonType() != null)
                predicates.add(cb.equal(root.get("seasonType"),request.seasonType()));

            if(request.minPrice() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"),request.minPrice()));

            if(request.maxPrice() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("price"),request.maxPrice()));

            predicates.add(cb.equal(root.get("isActive"),true));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
