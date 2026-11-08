package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.request.WheelFilterRequest;
import com.tirestore.tireshop.dto.response.WheelResponse;
import com.tirestore.tireshop.service.WheelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/wheel")
public class WheelController {

    private final WheelService wheelService;

    public WheelController(WheelService wheelService) {
        this.wheelService = wheelService;
    }


    @GetMapping("/get-all")
    public List<WheelResponse> getAllWheels() {
        return wheelService.getWheelsForUser();
    }

    @GetMapping("/get/{id}")
    public WheelResponse getWheelById(@PathVariable Integer id) {
        return wheelService.getWheelById(id);
    }

    @PostMapping("/filtered")
    public Page<WheelResponse> getFilteredWheels(
            @RequestBody WheelFilterRequest request,
            @PageableDefault(size = 20, sort = "price") Pageable pageable
    ){
        return wheelService.getFilteredWheelsForUser(request,pageable);
    }
}
