package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("""
        SELECT oi.item.itemId
        FROM OrderItem oi
        WHERE oi.item.isActive = true
        GROUP BY oi.item.itemId
        ORDER BY SUM(oi.quantity) DESC
        """)
    List<Integer> findMostPopularItemIds(Pageable pageable);
}
