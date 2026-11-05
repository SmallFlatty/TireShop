package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.response.WheelResponse;
import com.tirestore.tireshop.service.WheelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
