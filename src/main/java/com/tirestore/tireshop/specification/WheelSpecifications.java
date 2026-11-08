package com.tirestore.tireshop.specification;

import com.tirestore.tireshop.dto.request.WheelFilterRequest;
import com.tirestore.tireshop.entity.Wheel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WheelSpecifications {

    public static Specification<Wheel> getWheelSpecifications(WheelFilterRequest request) {
       return (root, _, cb) -> {
           List<Predicate> predicates = new ArrayList<>();


           if (request.holesNum() != null)
               predicates.add(cb.equal(root.get("holesNum"), request.holesNum()));
           if(request.circleDiameter() != null)
               predicates.add(cb.equal(root.get("circleDiameter"), request.circleDiameter()));
           if(request.width() != null)
               predicates.add(cb.equal(root.get("width"), request.width()));
           if(request.diameter() != null)
               predicates.add(cb.equal(root.get("diameter"), request.diameter()));
           if(request.minPrice() != null)
               predicates.add(cb.greaterThanOrEqualTo(root.get("price"),request.minPrice()));
           if(request.maxPrice() != null)
               predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.maxPrice()));

           predicates.add(cb.equal(root.get("isActive"),true));

           return cb.and(predicates.toArray(new Predicate[0]));
       };
    }
}
