package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.service.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/{id}/deposit")
    ApiResponse<TransactionResponse> deposit(@PathVariable String id, @RequestBody @Valid TransactionRequest request){
        return ApiResponse.<TransactionResponse>builder()
                .code(200)
                .message("OK")
                .result(transactionService.deposit(id, request))
                .build();
    }
    @PostMapping("/{id}/withdraw")
    ApiResponse<TransactionResponse> withdraw(@PathVariable String id, @RequestBody @Valid TransactionRequest request){
        return ApiResponse.<TransactionResponse>builder()
                .code(200)
                .message("OK")
                .result(transactionService.withdraw(id, request))
                .build();
    }
    @PostMapping("/{id}/transfer")
    ApiResponse<TransactionResponse> transfer(@PathVariable String id, @RequestBody @Valid TransactionRequest request){
        return ApiResponse.<TransactionResponse>builder()
                .code(200)
                .message("OK")
                .result(transactionService.transfer(id, request))
                .build();
    }

}
