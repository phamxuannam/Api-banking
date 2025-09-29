package com.phmxnnam.prj_banking.event;

import com.phmxnnam.prj_banking.enums.AuditActionEnum;
import lombok.Builder;

@Builder
public record TransactionActionEvent(
        String transactionId,
        AuditActionEnum auditAction,
        String statusBefore,
        String statusAfter,
        double amount,
        String fromAccountNumber,
        String toAccountNumber,
        String actor,
        String actorId
) {}
