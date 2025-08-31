package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.AccountRequest;
import com.phmxnnam.prj_banking.dto.response.AccountResponse;
import com.phmxnnam.prj_banking.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "customer", ignore = true)
    AccountEntity toEntity(AccountRequest request);

    @Mapping(source = "customer.id", target = "customer_id")
    @Mapping(source = "isActive", target = "status")
    AccountResponse toResponse(AccountEntity entity);

}
