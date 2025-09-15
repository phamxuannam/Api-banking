package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.AccountRequest;
import com.phmxnnam.prj_banking.dto.response.AccountResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring", uses = { TransactionMapper.class } )
public interface AccountMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "transactionFrom", ignore = true)
    @Mapping(target = "transactionTo", ignore = true)
    AccountEntity toEntity(AccountRequest request);

    @Mapping(source = "customer.id", target = "customer_id")
    @Mapping(source = "isActive", target = "status")
    AccountResponse toResponse(AccountEntity entity);

}
