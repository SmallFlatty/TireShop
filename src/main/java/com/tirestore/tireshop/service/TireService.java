package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.TireRequest;
import com.tirestore.tireshop.dto.response.TireResponse;
import com.tirestore.tireshop.entity.Tire;
import com.tirestore.tireshop.exception.ResourceNotFoundException;
import com.tirestore.tireshop.repository.TireRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class TireService {

    private final TireRepository tireRepository;
    private final ImageService imageService;

    public TireService(TireRepository tireRepository, ImageService imageService) {
        this.tireRepository = tireRepository;
        this.imageService = imageService;
    }


    public List<TireResponse> getTiresForUser() {
        return tireRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<TireResponse> getTiresForAdmin() {
        return tireRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TireResponse mapToResponse(Tire t) {
        return new TireResponse(
                t.getItemId(),
                t.getName(),
                t.getDescription(),
                t.getPrice(),
                t.getPublicId(),
                t.getWidth(),
                t.getProfile(),
                t.getSpeedRating(),
                t.getDiameter(),
                t.getSeasonType(),
                t.getStock()
        );
    }

    public TireResponse getTireById(Integer id) {
        Tire t = tireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tire with id " + id + " not found"));

        return mapToResponse(t);
    }

    @Transactional
    public TireResponse saveTire(TireRequest tireRequest, MultipartFile file) {
        String publicId;

        char speedRating = tireRequest.speedRating().charAt(0);

        try {
            publicId = imageService.uploadImage(file);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Upload image failed");
        }

        Tire t = new Tire(tireRequest.name(), tireRequest.description(), publicId, tireRequest.price(), tireRequest.stock(), tireRequest.width(), tireRequest.profile(),
                speedRating, tireRequest.diameter(), tireRequest.seasonType());
        tireRepository.save(t);

        return mapToResponse(t);
    }

    @Transactional
    public TireResponse updateTire(int id, TireRequest tireRequest) {
        Tire t = tireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tire with id " + id + " not found"));

        t.setName(tireRequest.name());
        t.setDescription(tireRequest.description());
        t.setPrice(tireRequest.price());
        t.setStock(tireRequest.stock());

        t.setWidth(tireRequest.width());
        t.setProfile(tireRequest.profile());
        t.setSpeedRating(tireRequest.speedRating().charAt(0));
        t.setDiameter(tireRequest.diameter());
        t.setSeasonType(tireRequest.seasonType());

        tireRepository.save(t);

        return mapToResponse(t);
    }


    @Transactional
    public void deactivateTire(Integer id) {
        Tire t = tireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tire with id " + id + " not found"));
        t.setIsActive(false);
        tireRepository.save(t);
    }

    @Transactional
    public TireResponse updateImage(Integer id, MultipartFile file) {
        Tire t = tireRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id " + id + " not found"));

        String publicId = t.getPublicId();

        imageService.deleteImage(publicId);

        String newPublicId = imageService.uploadImage(file);
        t.setPublicId(newPublicId);

        tireRepository.save(t);

        return mapToResponse(t);
    }
}
