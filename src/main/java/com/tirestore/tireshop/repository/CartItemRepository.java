package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.entity.CartItem;
import com.tirestore.tireshop.entity.id.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}
