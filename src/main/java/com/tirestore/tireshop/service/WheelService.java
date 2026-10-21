package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.WheelRequest;
import com.tirestore.tireshop.dto.response.TireResponse;
import com.tirestore.tireshop.dto.response.WheelResponse;
import com.tirestore.tireshop.entity.Wheel;
import com.tirestore.tireshop.exception.ResourceNotFoundException;
import com.tirestore.tireshop.repository.WheelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class WheelService {

    private final WheelRepository wheelRepository;
    private final ImageService imageService;

    public WheelService(WheelRepository wheelRepository, ImageService imageService) {
        this.wheelRepository = wheelRepository;

        this.imageService = imageService;
    }

    private WheelResponse mapToResponse(Wheel w) {
        return new WheelResponse(
                w.getItemId(),
                w.getName(),
                w.getDescription(),
                w.getPrice(),
                w.getPublicId(),
                w.getDiameter(),
                w.getWidth(),
                w.getCircleDiameter(),
                w.getHolesNum(),
                w.getStock()
        );
    }

    public List<WheelResponse> getTiresForAdmin(){
        return wheelRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<WheelResponse> getTiresForUser(){
        return wheelRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WheelResponse getWheelById(Integer id){
        Wheel w =  wheelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wheel with id " + id + " not found"));

        return mapToResponse(w);
    }

    @Transactional
    public WheelResponse addWheel(WheelRequest wheelRequest, MultipartFile file){
        String publicId;

        try {
            publicId = imageService.uploadImage(file);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Upload image failed");
        }

        Wheel w = new Wheel(
                wheelRequest.name(),
                wheelRequest.description(),
                publicId,
                wheelRequest.price(),
                wheelRequest.stock(),
                wheelRequest.holesNum(),
                wheelRequest.circleDiameter(),
                wheelRequest.width(),
                wheelRequest.diameter()
        );

        wheelRepository.save(w);

        return mapToResponse(w);
    }

    @Transactional
    public WheelResponse updateWheel(Integer id, WheelRequest wheelRequest){
        Wheel w = wheelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wheel with id " + id + " not found"));

        w.setName(wheelRequest.name());
        w.setDescription(wheelRequest.description());
        w.setPrice(wheelRequest.price());
        w.setStock(wheelRequest.stock());

        w.setHolesNum(wheelRequest.holesNum());
        w.setCircleDiameter(wheelRequest.circleDiameter());
        w.setWidth(wheelRequest.width());
        w.setDiameter(wheelRequest.diameter());

        wheelRepository.save(w);

        return mapToResponse(w);
    }

    @Transactional
    public void deactivateWheel(Integer id){
        Wheel w = wheelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wheel with id " + id + " not found"));
        w.setIsActive(false);
        wheelRepository.save(w);
    }

    @Transactional
    public WheelResponse updateImage(Integer id, MultipartFile file){
        Wheel w = wheelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wheel with id " + id + " not found"));

        String publicId = w.getPublicId();

        imageService.deleteImage(publicId);

        String newPublicId = imageService.uploadImage(file);
        w.setPublicId(newPublicId);

        wheelRepository.save(w);

        return mapToResponse(w);
    }
}
