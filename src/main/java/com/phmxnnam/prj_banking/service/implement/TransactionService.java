package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;
import com.phmxnnam.prj_banking.entity.TransactionEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.TransactionMapper;
import com.phmxnnam.prj_banking.repository.AccountRepository;
import com.phmxnnam.prj_banking.repository.TransactionRepository;
import com.phmxnnam.prj_banking.service.ITransactionService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class TransactionService implements ITransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    AccountRepository accountRepository;

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

        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

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

        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse transfer(String id, TransactionRequest request) {
        AccountEntity toAccount = accountRepository.findByAccountNumber(request.getToAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS) );

        AccountEntity fromAccount = accountRepository.findByAccountNumber(request.getFromAccount()).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS) );

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
        transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

}
