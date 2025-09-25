package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.CustomerCreationRequest;
import com.phmxnnam.prj_banking.dto.request.CustomerUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.CustomerResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.CustomerMapper;
import com.phmxnnam.prj_banking.repository.CustomerRepository;
import com.phmxnnam.prj_banking.service.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @PreAuthorize("hasRole('teller')")
    @Override
    public CustomerResponse create(CustomerCreationRequest request) {
        if(customerRepository.existsByIdentification(request.getIdentification()))
            throw new AppException(ErrorCode.USER_EXISTED);
        CustomerEntity customers = customerMapper.toEntity(request);

        return customerMapper.toResponse(customerRepository.save(customers));
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('permission:read')")
    @Override
    public List<CustomerResponse> getAll(){
        return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('teller')")
    @Override
    public CustomerResponse getById(String id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return customerMapper.toResponse(customer);
    }

    @PostAuthorize("returnObject.username ==  authentication.name")
    @PreAuthorize("hasAuthority('permission:edit')")
    @Override
    public CustomerResponse updateById(CustomerUpdateRequest request, String id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        customerMapper.updateCustomer(customer, request);

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String deleteCus(String id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        customerRepository.deleteById(id);
        return "Deleted Customer.";
    }
}
