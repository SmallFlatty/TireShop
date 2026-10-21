package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


}
