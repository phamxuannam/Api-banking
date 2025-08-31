package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findAllByRoleName(@Param("roleName") String roleName);

}
