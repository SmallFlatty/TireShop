package com.tirestore.tireshop.controller.admin;


import com.tirestore.tireshop.dto.request.TireRequest;
import com.tirestore.tireshop.dto.request.WheelRequest;
import com.tirestore.tireshop.dto.response.TireResponse;
import com.tirestore.tireshop.dto.response.WheelResponse;
import com.tirestore.tireshop.service.TireService;
import com.tirestore.tireshop.service.WheelService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/item")
@PreAuthorize("hasRole('ADMIN')")
public class AdminItemController {

    private final TireService tireService;
    private final WheelService wheelService;

    //Tire Part

    public AdminItemController(TireService tireService, WheelService wheelService) {
        this.tireService = tireService;
        this.wheelService = wheelService;
    }

    @GetMapping("/get-tires")
    public List<TireResponse> getTires() {
        return tireService.getTiresForAdmin();
    }

    @PostMapping("/add-tire")
    public TireResponse saveTire(@RequestPart @Valid TireRequest tireRequest, @RequestPart MultipartFile image) {
       return tireService.saveTire(tireRequest, image);
    }

    @PutMapping("/update-tire/{id}")
    public TireResponse updateTire(@RequestBody TireRequest tireRequest,@PathVariable Integer id ) {
        return tireService.updateTire(id, tireRequest);
    }

    @DeleteMapping("/deactivate-tire/{id}")
    public void deactivateTire(@PathVariable Integer id) {
        tireService.deactivateTire(id);
    }

    @PutMapping("/update-img-tire/{id}")
    public TireResponse updateImageTire(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return tireService.updateImage(id, image);
    }

    // Wheel Part

    @GetMapping("/get-wheels")
    public List<WheelResponse> getWheels() {
        return wheelService.getWheelsForAdmin();
    }

    @PostMapping("/add-wheel")
    public WheelResponse addWheel(@RequestPart @Valid WheelRequest wheelRequest,  @RequestPart MultipartFile image) {
        return wheelService.addWheel(wheelRequest, image);
    }

    @PutMapping("/update-wheel/{id}")
    public WheelResponse updateWheel(@PathVariable Integer id, @RequestBody WheelRequest wheelRequest) {
        return wheelService.updateWheel(id, wheelRequest);
    }

    @DeleteMapping("/deactivate-wheel/{id}")
    public void deactivateWheel(@PathVariable Integer id) {
        wheelService.deactivateWheel(id);
    }

    @PutMapping("/update-img-wheel/{id}")
    public WheelResponse updateImageWheel(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return wheelService.updateImage(id, image);
    }
}
