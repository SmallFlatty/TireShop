package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    void deactivateItem(Integer id);
    String replaceImage(Integer id, MultipartFile image);
}
