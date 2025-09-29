package com.phmxnnam.prj_banking.entity;

import com.phmxnnam.prj_banking.enums.AuditActionEnum;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "audit_logs",
        indexes = { @Index(name = "idx_audit_tran_time", columnList = "transactionId, createdAt"),
                    @Index(name = "idx_audit_actor_time", columnList = "actor, actorId, createdAt")
        })
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "transaction_id", nullable = false)
    String transactionId;

    @Enumerated(EnumType.STRING)
    @Column
    AuditActionEnum auditAction;

    @Column
    String statusBefore;

    @Column
    String statusAfter;

    @Column
    double amount;

    @Column
    String fromAccountNumber;

    @Column
    String toAccountNumber;

    @Column
    String actor;

    @Column(name = "actor_id")
    String actorId;

    @CreationTimestamp
    @Column
    LocalDate createdAt;

    @PreUpdate
    private void preventUpdate(){ throw new AppException(ErrorCode.APPEND_ONLY); }

    @PreRemove
    private void preventDelete(){ throw new AppException(ErrorCode.APPEND_ONLY); }
}
