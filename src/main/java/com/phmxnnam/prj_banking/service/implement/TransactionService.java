package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.service.ITransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Override
    public TransactionResponse create(TransactionRequest request) {
        return null;
    }

    @Override
    public List<TransactionResponse> getAll() {
        return List.of();
    }

    @Override
    public TransactionResponse getTranById(String id) {
        return null;
    }

    @Override
    public String deleteById(String id) {
        return "";
    }
}
