package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.AccountRequest;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.enums.Role;
import com.tirestore.tireshop.exception.ResourceAlreadyExistsException;
import com.tirestore.tireshop.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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


}
