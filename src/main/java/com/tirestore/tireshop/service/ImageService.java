package com.tirestore.tireshop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File type not supported");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "tire-shop/tires")
            );
            return (String) result.get("id");
        } catch (IOException e) {
            throw new UncheckedIOException("Cloudinary upload failed", e);
        }
    }
    public void deleteImage(String publicId) {
        if(publicId == null || publicId.isEmpty()) {
            throw new IllegalArgumentException("Public ID is empty");
        }
        try{
            Map<?,?> result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("invalidate",true)
            );
            String resultStatus = (String) result.get("status");

            if(!resultStatus.equals("success")) {
                throw new IllegalStateException("Cloudinary delete failed" + resultStatus);
            }
        }catch(IOException e){
            throw new UncheckedIOException("Cloudinary delete failed", e);
        }
    }
}
