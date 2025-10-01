package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.AccountRequest;
import com.phmxnnam.prj_banking.dto.response.AccountResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.AccountMapper;
import com.phmxnnam.prj_banking.repository.AccountRepository;
import com.phmxnnam.prj_banking.repository.CustomerRepository;
import com.phmxnnam.prj_banking.service.IAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;
    CustomerRepository customerRepository;

    @PreAuthorize("hasRole('teller')")
    @Override
    public AccountResponse create(AccountRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomer_id()).orElseThrow( () ->
                new AppException(ErrorCode.CUSTOMER_NOT_EXISTS) );
        if(customer.getPhoneNumber() != request.getAccountNumber()) {
            if(accountRepository.existsByAccountNumber(request.getAccountNumber()))
                throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        } else {
            if (accountRepository.existsByAccountNumber(request.getAccountNumber()))
                throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }
        AccountEntity account = accountMapper.toEntity(request);
        account.setIsActive(1);
        account.setCustomer(customer);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('read')")
    @Override
    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream().map(account -> accountMapper.toResponse(account)).toList();
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('read')")
    @Override
    public AccountResponse getAccById(String id) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS));
        return accountMapper.toResponse(account);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('management')")
    @Override
    public String turnOnOffAccById(String id) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS));
        int active = 1;
        boolean on = true;
        if(account.getIsActive() == 1){
            active = 0;
            on = false;
        }
        account.setIsActive(active);
        accountRepository.save(account);
        if(on) return "turned on account.";
        else return "turned off account.";
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('management')")
    @Override
    public String deleteAccById(String id) {
        AccountEntity account = accountRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTS));
        accountRepository.delete(account);
        return "deleted account.";
    }
}
