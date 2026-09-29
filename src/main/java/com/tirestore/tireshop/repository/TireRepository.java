package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


@Repository
public interface TireRepository extends JpaRepository<Tire,Integer> {
}
