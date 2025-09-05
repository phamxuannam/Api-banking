package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;

import java.util.List;

public interface ITransactionService {
    TransactionResponse create(TransactionRequest request);
    List<TransactionResponse> getAll();
    TransactionResponse getTranById(String id);
    String deleteById(String id);
}
