package com.tirestore.tireshop.security;

import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.repository.AccountRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            return new UserDetailsImpl(account.get());
        }else{
            throw new UsernameNotFoundException("Account not found" + email);
        }
    }
}
