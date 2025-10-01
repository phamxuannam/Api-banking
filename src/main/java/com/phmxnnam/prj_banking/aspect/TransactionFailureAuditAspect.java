package com.phmxnnam.prj_banking.aspect;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.enums.AuditActionEnum;
import com.phmxnnam.prj_banking.event.TransactionActionEvent;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IAuditLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionFailureAuditAspect {

    IAuditLogService auditLogService;
    UserRepository userRepository;

    private UserEntity currentActor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return user;
    }

    @AfterThrowing(
            pointcut = "execution(* com.phmxnnam.prj_banking.service.ITransactionService.deposit(..)) && args(id, request)",
            throwing = "ex")
    public void depositFailure(JoinPoint jp, String id, TransactionRequest request, AppException ex){

        auditLogService.appendFailure(TransactionActionEvent.builder()
                        .transactionId("null")
                        .auditAction(AuditActionEnum.DEPOSIT)
                        .statusBefore(null)
                        .statusAfter("FAIL")
                        .amount(request.getAmount())
                        .actor(currentActor().getUsername())
                        .actorId(currentActor().getId())
                        .fromAccountNumber(null)
                        .toAccountNumber(request.getToAccount())
                .build());
    }

    @AfterThrowing(
            pointcut = "execution(* com.phmxnnam.prj_banking.service.ITransactionService.withdraw(..)) && args(id, request)",
            throwing = "ex")
    public void withdrawFailure(JoinPoint jp, String id, TransactionRequest request, AppException ex){

        auditLogService.appendFailure(TransactionActionEvent.builder()
                        .transactionId("null")
                        .auditAction(AuditActionEnum.WITHDRAW)
                        .statusBefore(null)
                        .statusAfter("FAIL")
                        .amount(request.getAmount())
                        .actor(currentActor().getUsername())
                        .actorId(currentActor().getId())
                        .fromAccountNumber(request.getFromAccount())
                        .toAccountNumber(null)
                .build());
    }

    @AfterThrowing(
            pointcut = "execution(* com.phmxnnam.prj_banking.service.ITransactionService.transfer(..)) && args(id, request)",
            throwing = "ex")
    public void transferFailure(JoinPoint jp, String id, TransactionRequest request, AppException ex){

        auditLogService.appendFailure(TransactionActionEvent.builder()
                .transactionId("null")
                .auditAction(AuditActionEnum.TRANSFER)
                .statusBefore(null)
                .statusAfter("FAIL")
                .amount(request.getAmount())
                .actor(currentActor().getUsername())
                .actorId(currentActor().getId())
                .fromAccountNumber(request.getFromAccount())
                .toAccountNumber(request.getToAccount())
                .build());
    }

}
