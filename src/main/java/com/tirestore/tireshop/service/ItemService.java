package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.response.PopularItemResponse;
import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.repository.OrderItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final OrderItemRepository orderItemRepository;

    public ItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<Item> findMostPopularItems (Pageable pageable) {
        return orderItemRepository.findMostPopularItems(pageable);
    }

    public List<PopularItemResponse> getPopularItems () {
        List<Item> items = findMostPopularItems(Pageable.unpaged());
        List<PopularItemResponse> popularItemResponses = new ArrayList<>();
        for (Item item : items) {
            popularItemResponses.add(new PopularItemResponse(
                    item.getItemId(),
                    item.getName(),
                    item.getPublicId(),
                    item.getPrice()
            ));
        }
        return popularItemResponses;
    }
}
