package com.tirestore.tireshop.service;

import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {

    private final AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


}
