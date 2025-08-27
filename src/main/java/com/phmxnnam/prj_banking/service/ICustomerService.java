package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.CustomerCreationRequest;
import com.phmxnnam.prj_banking.dto.request.CustomerUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    CustomerResponse create(CustomerCreationRequest request);
    List<CustomerResponse> getAll();
    CustomerResponse getById(String id);
    CustomerResponse updateById(CustomerUpdateRequest request, String id);
    void turnOnOfById(String id);
    void deleteById(String id);
}
