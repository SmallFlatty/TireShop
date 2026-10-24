package com.tirestore.tireshop;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TireShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TireShopApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinary(@Value("${CLOUDINARY_URL}") String url) {
        Cloudinary cloudinary = new Cloudinary(url);
        cloudinary.config.secure = true;
        return cloudinary;
    }
}
