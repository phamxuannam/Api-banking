package com.phmxnnam.prj_banking.repository;

import com.phmxnnam.prj_banking.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String> {
}
