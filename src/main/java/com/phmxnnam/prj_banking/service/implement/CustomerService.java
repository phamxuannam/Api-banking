package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.CustomerCreationRequest;
import com.phmxnnam.prj_banking.dto.request.CustomerUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.CustomerResponse;
import com.phmxnnam.prj_banking.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Override
    public CustomerResponse create(CustomerCreationRequest request) {
        return null;
    }

    @Override
    public List<CustomerResponse> getAll() {
        return List.of();
    }

    @Override
    public CustomerResponse getById(String id) {
        return null;
    }

    @Override
    public CustomerResponse updateById(CustomerUpdateRequest request, String id) {
        return null;
    }

    @Override
    public void turnOnOfById(String id) {

    }

    @Override
    public void deleteById(String id) {

    }
}
