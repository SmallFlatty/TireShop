package com.tirestore.tireshop.security;

import com.tirestore.tireshop.entity.Account;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private final Account account;

    public UserDetailsImpl(Account account) {
        this.account = account;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + account.getRole().toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return account.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }
}
