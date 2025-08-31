package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    boolean existsByIdentification(String identification);

    boolean existsByPhoneNumber(String phoneNumber);
}
