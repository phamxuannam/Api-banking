package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    boolean existsByAccountNumber(String accountNumber);
}
