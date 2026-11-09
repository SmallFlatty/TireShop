package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.response.PopularItemResponse;
import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.repository.ItemRepository;
import com.tirestore.tireshop.repository.OrderItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    public ItemService(OrderItemRepository orderItemRepository, ItemRepository itemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
    }

    public List<Integer> findMostPopularItems (Pageable pageable) {
        return orderItemRepository.findMostPopularItemIds(pageable);
    }

    public List<PopularItemResponse> getPopularItems () {
        List<Item> items =itemRepository.findAllById(findMostPopularItems(Pageable.unpaged()));
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
