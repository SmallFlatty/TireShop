package com.tirestore.tireshop.repository;

import com.tirestore.tireshop.dto.request.AccountRequest;
import com.tirestore.tireshop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    void createAccount(AccountRequest accountRequest);
}
