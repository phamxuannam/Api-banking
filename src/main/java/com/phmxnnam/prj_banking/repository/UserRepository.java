package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByUsername(String username);
}
