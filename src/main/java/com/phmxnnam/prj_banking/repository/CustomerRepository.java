package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
