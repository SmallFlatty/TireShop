package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Wheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelRepository extends JpaRepository<Wheel, Integer> {
}
