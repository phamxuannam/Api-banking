package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
