package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.AccountRequest;
import com.phmxnnam.prj_banking.dto.response.AccountResponse;

import java.util.List;

public interface IAccountService {
    AccountResponse create(AccountRequest request);
    List<AccountResponse> getAll();
    AccountResponse getAccById(String id);
    String turnOnOffAccById(String id);
    String deleteAccById(String id);
}
