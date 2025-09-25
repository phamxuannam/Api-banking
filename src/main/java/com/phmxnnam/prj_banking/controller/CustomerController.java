package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.CustomerCreationRequest;
import com.phmxnnam.prj_banking.dto.request.CustomerUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.CustomerResponse;
import com.phmxnnam.prj_banking.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    ApiResponse<CustomerResponse> create(@RequestBody @Valid CustomerCreationRequest request){
        return ApiResponse.<CustomerResponse>builder()
                .code(200)
                .message("OK")
                .result(customerService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CustomerResponse>> getAll(){
        return ApiResponse.<List<CustomerResponse>>builder()
                .code(200)
                .message("OK")
                .result(customerService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CustomerResponse> getById(@PathVariable String id){
        return ApiResponse.<CustomerResponse>builder()
                .code(200)
                .message("OK")
                .result(customerService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CustomerResponse> updateById(@PathVariable String id, @RequestBody @Valid CustomerUpdateRequest request){
        return ApiResponse.<CustomerResponse>builder()
                .code(200)
                .message("OK")
                .result(customerService.updateById(request, id))
                .build();
    }

    @DeleteMapping
    ApiResponse<String> deleteCus(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(customerService.deleteCus(id))
                .build();
    }
}
