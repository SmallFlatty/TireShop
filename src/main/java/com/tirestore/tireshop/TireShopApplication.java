package com.tirestore.tireshop;

import com.cloudinary.Cloudinary;
import com.tirestore.tireshop.dto.request.AccountRequest;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TireShopApplication implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(TireShopApplication.class, args);
    }

    @Autowired
    private AuthService authService;

    @Bean
    public Cloudinary cloudinary(@Value("${CLOUDINARY_URL}") String url) {
        Cloudinary cloudinary = new Cloudinary(url);
        cloudinary.config.secure = true;
        return cloudinary;
    }


    @Override
    public void run(String... args) throws Exception {
        AccountRequest adminAcc = new AccountRequest(
               "Admin",
               "VeRySaFeePasWord1999",
               "admin.email@gmail.com"
        );

        authService.createAdminAccount(adminAcc);
    }
}
