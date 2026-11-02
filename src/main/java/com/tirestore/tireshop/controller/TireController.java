package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.response.TireResponse;
import com.tirestore.tireshop.service.TireService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tire")
public class TireController {
    private final TireService tireService;

    public TireController(TireService tireService) {
        this.tireService = tireService;
    }

    @GetMapping("/get-all")
    public List<TireResponse> getAllTires() {
        return tireService.getTiresForUser();
    }

    @GetMapping("/get/{id}")
    public TireResponse getTireById(@PathVariable Integer id) {
        return tireService.getTireById(id);
    }
}
