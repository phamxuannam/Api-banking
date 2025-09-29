package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;

public interface ITransactionService {
    String currentUserInfo();
    TransactionResponse deposit(String id, TransactionRequest request);
    TransactionResponse withdraw(String id, TransactionRequest request);
    TransactionResponse transfer(String id, TransactionRequest request);
 //   boolean checkAccount(String accountNumber, boolean isTransfer);
}
