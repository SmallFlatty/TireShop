package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.request.TireFilterRequest;
import com.tirestore.tireshop.dto.response.TireResponse;
import com.tirestore.tireshop.service.TireService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("filtered")
    public Page<TireResponse> getFilteredTires(
            @RequestBody TireFilterRequest request,
            @ParameterObject @PageableDefault(size = 20, sort = "price") Pageable pageable ) {

        return tireService.getFilteredTiresForUser(request,pageable);
    }
}
