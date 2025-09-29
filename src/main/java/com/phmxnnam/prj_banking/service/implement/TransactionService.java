package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;
import com.phmxnnam.prj_banking.entity.TransactionEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.enums.AuditActionEnum;
import com.phmxnnam.prj_banking.event.TransactionActionEvent;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.TransactionMapper;
import com.phmxnnam.prj_banking.repository.AccountRepository;
import com.phmxnnam.prj_banking.repository.TransactionRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.ITransactionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(rollbackFor = AppException.class)
public class TransactionService implements ITransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    AccountRepository accountRepository;
    UserRepository userRepository;
    ApplicationEventPublisher eventPublisher;

    public String currentUserInfo(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PreAuthorize("hasRole('customer') || hasRole('teller')")
    @Override
    public TransactionResponse deposit(String id, TransactionRequest request) {

        AccountEntity toAccount = accountRepository.findByAccountNumber(request.getToAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS) );

        if(!id.equals(toAccount.getId())) throw new AppException(ErrorCode.ACCOUNT_NOT_EXISTS);
        if(toAccount.getIsActive() != 1) throw new AppException(ErrorCode.ACCOUNT_INACTIVE);

        double before = toAccount.getBalance();
        double after = toAccount.getBalance() + request.getAmount();
        toAccount.setBalance(after);
        accountRepository.save(toAccount);

        TransactionEntity transaction = transactionMapper.toEntity(request);
        transaction.setFromAccount(null);
        transaction.setToAccount(toAccount);
        transaction.setFromAccountNumber(null);
        transaction.setToAccountNumber(toAccount.getAccountNumber());
        transaction.setBalancerBefore(before);
        transaction.setBalancerAfter(after);
        transaction.setStatus("done");

        TransactionEntity saveTransaction = transactionRepository.save(transaction);
        UserEntity user = userRepository.findByUsername(currentUserInfo()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTS));

        eventPublisher.publishEvent(TransactionActionEvent.builder()
                        .transactionId(saveTransaction.getId())
                        .auditAction(AuditActionEnum.DEPOSIT)
                        .statusBefore(null)
                        .statusAfter("DONE")
                        .amount(request.getAmount())
                        .actor(currentUserInfo())
                        .actorId(user.getId())
                        .fromAccountNumber(null)
                        .toAccountNumber(saveTransaction.getToAccountNumber())
                .build());

        return transactionMapper.toResponse(saveTransaction);
    }

    @PreAuthorize("hasRole('customer') || hasRole('teller') || hasAuthority('permission: approve')")
    @Override
    public TransactionResponse withdraw(String id, TransactionRequest request) {
        AccountEntity fromAccount = accountRepository.findByAccountNumber(request.getFromAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS) );

        if(!id.equals(fromAccount.getId())) throw new AppException(ErrorCode.ACCOUNT_NOT_EXISTS);
        if(fromAccount.getIsActive() != 1) throw new AppException(ErrorCode.ACCOUNT_INACTIVE);

        if(fromAccount.getBalance() < request.getAmount()) throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        if(request.getAmount() < 50) throw new AppException(ErrorCode.MIN_AMOUNT);

        double before = fromAccount.getBalance();
        double after = fromAccount.getBalance() - request.getAmount();
        fromAccount.setBalance(after);
        accountRepository.save(fromAccount);

        TransactionEntity transaction = transactionMapper.toEntity(request);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(null);
        transaction.setFromAccountNumber(fromAccount.getAccountNumber());
        transaction.setToAccountNumber(null);
        transaction.setBalancerBefore(before);
        transaction.setBalancerAfter(after);
        transaction.setStatus("done");

        TransactionEntity saveTransaction = transactionRepository.save(transaction);
        UserEntity user = userRepository.findByUsername(currentUserInfo()).orElseThrow( () -> new AppException(ErrorCode.USER_NOT_EXISTS));

        eventPublisher.publishEvent(TransactionActionEvent.builder()
                .transactionId(saveTransaction.getId())
                .auditAction(AuditActionEnum.WITHDRAW)
                .statusBefore(null)
                .statusAfter("DONE")
                .amount(request.getAmount())
                .actor(currentUserInfo())
                .actorId(user.getId())
                .fromAccountNumber(saveTransaction.getToAccountNumber())
                .toAccountNumber(null)
                .build());

        return transactionMapper.toResponse(saveTransaction);
    }

    @PreAuthorize("hasAuthority('permission:transfer')")
    @Override
    public TransactionResponse transfer(String id, TransactionRequest request) {
        AccountEntity toAccount = accountRepository.findByAccountNumber(request.getToAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS) );

        AccountEntity fromAccount = accountRepository.findByAccountNumber(request.getFromAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS));

        if(! (id.equals(fromAccount.getId())) || (id.equals(toAccount.getId())) ) throw new AppException(ErrorCode.ACCOUNT_NOT_EXISTS);
        if(toAccount.getIsActive() != 1) throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
        if(fromAccount.getBalance() < request.getAmount()) throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);

        double beforeFromAcc = fromAccount.getBalance();
        double afterFromAcc = fromAccount.getBalance() - request.getAmount();
        fromAccount.setBalance(afterFromAcc);
        accountRepository.save(fromAccount);

        double beforeToAcc = toAccount.getBalance();
        double afterToAcc = toAccount.getBalance() + request.getAmount();
        toAccount.setBalance(afterToAcc);
        accountRepository.save(toAccount);

        TransactionEntity transaction = transactionMapper.toEntity(request);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setFromAccountNumber(fromAccount.getAccountNumber());
        transaction.setToAccountNumber(toAccount.getAccountNumber());
        transaction.setStatus("done");
        if(id.equals(fromAccount.getId())){
            transaction.setBalancerBefore(beforeFromAcc);
            transaction.setBalancerAfter(afterFromAcc);
        } else if(id.equals(toAccount.getId())){
            transaction.setBalancerBefore(beforeToAcc);
            transaction.setBalancerAfter(afterToAcc);
        }

        TransactionEntity saveTransaction = transactionRepository.save(transaction);
        UserEntity user = userRepository.findByUsername(currentUserInfo()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTS));

        eventPublisher.publishEvent(TransactionActionEvent.builder()
                .transactionId(saveTransaction.getId())
                .auditAction(AuditActionEnum.TRANSFER)
                .statusBefore(null)
                .statusAfter("DONE")
                .amount(request.getAmount())
                .actor(currentUserInfo())
                .actorId(user.getId())
                .fromAccountNumber(saveTransaction.getFromAccountNumber())
                .toAccountNumber(saveTransaction.getToAccountNumber())
                .build());

        return transactionMapper.toResponse(saveTransaction);
    }

}
