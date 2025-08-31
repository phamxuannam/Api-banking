package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.AccountRequest;
import com.phmxnnam.prj_banking.dto.response.AccountResponse;
import com.phmxnnam.prj_banking.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    ApiResponse<AccountResponse> create(@RequestBody AccountRequest request){
        return ApiResponse.<AccountResponse>builder()
                .code(200)
                .message("OK")
                .result(accountService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<AccountResponse>> getAll(){
        return ApiResponse.<List<AccountResponse>>builder()
                .code(200)
                .message("OK")
                .result(accountService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<AccountResponse> getAccById(@PathVariable String id){
        return ApiResponse.<AccountResponse>builder()
                .code(200)
                .message("OK")
                .result(accountService.getAccById(id))
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<String> turnOnOffAccById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(accountService.turnOnOffAccById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteAccById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(accountService.deleteAccById(id))
                .build();
    }
}
