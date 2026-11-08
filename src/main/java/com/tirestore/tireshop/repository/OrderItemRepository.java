package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("SELECT Item FROM OrderItem oi GROUP BY oi.item ORDER BY COUNT(oi.item) DESC")
    List<Item> findMostPopularItems(Pageable pageable);
}
