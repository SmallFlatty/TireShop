package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.AccountRequest;
import com.tirestore.tireshop.dto.request.LoginRequest;
import com.tirestore.tireshop.dto.response.LoginResponse;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.enums.Role;
import com.tirestore.tireshop.exception.ResourceAlreadyExistsException;
import com.tirestore.tireshop.repository.AccountRepository;
import com.tirestore.tireshop.security.JwtService;
import com.tirestore.tireshop.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public String createAccount(AccountRequest accountRequest) {

        accountRepository.findByEmail(accountRequest.email()).ifPresentOrElse(
                account -> {
                    throw new ResourceAlreadyExistsException("Account already exists");
                },() -> {
                    accountRepository.save(new Account(
                            Role.CUSTOMER,
                            accountRequest.fullName(),
                            bCryptPasswordEncoder.encode(accountRequest.password()),
                            accountRequest.email()
                    )
                );
                }
        );
        return "Your account has been created";
    }

    public LoginResponse login(LoginRequest request) {
       Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               request.email(), request.password())
       );
       UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
       String token = jwtService.generateToken(userDetails);
       long expiresIn = jwtService.getExpirationMs();

        String fullName = userDetails.getAccount().getFullName();
        Role role = userDetails.getAccount().getRole();

       return new LoginResponse(token,expiresIn, fullName,role);
    }


}
