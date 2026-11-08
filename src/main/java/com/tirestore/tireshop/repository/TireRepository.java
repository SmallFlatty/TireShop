package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TireRepository extends JpaRepository<Tire,Integer>, JpaSpecificationExecutor<Tire> {
    List<Tire> findByIsActiveTrue();
}
