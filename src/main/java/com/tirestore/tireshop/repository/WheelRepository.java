package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.dto.request.WheelRequest;
import com.tirestore.tireshop.dto.response.WheelResponse;
import com.tirestore.tireshop.entity.Wheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface WheelRepository extends JpaRepository<Wheel, Integer> {
    List<Wheel> findByIsActiveTrue();
}
