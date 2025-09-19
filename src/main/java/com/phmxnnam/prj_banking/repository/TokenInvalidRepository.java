package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.TokenInvalidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenInvalidRepository extends JpaRepository<TokenInvalidEntity, String> {
    boolean existsByIdToken(String idToken);
}
