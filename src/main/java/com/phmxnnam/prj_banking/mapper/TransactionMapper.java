package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.TransactionRequest;
import com.phmxnnam.prj_banking.dto.response.TransactionResponse;
import com.phmxnnam.prj_banking.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "fromAccount", ignore = true)
    @Mapping(target = "toAccount", ignore = true)
    TransactionEntity toEntity(TransactionRequest request);

    @Mapping(source = "fromAccount.id", target = "fromAccount")
    @Mapping(source = "toAccount.id", target = "toAccount")
    TransactionResponse toResponse(TransactionEntity entity);

}
