package com.tirestore.tireshop.controller;


import com.tirestore.tireshop.dto.request.AccountRequest;
import com.tirestore.tireshop.dto.request.LoginRequest;
import com.tirestore.tireshop.dto.response.LoginResponse;
import com.tirestore.tireshop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/auth/create-acc")
    public String createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return authService.createAccount(accountRequest);
    }

}
