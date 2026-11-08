package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.response.PopularItemResponse;
import com.tirestore.tireshop.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/get-pop-products")
    public List<PopularItemResponse> getPopularItems() {
        return itemService.getPopularItems();
    }
}
