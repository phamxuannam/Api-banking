package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.CustomerCreationRequest;
import com.phmxnnam.prj_banking.dto.request.CustomerUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.CustomerResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "users", ignore = true)
    CustomerEntity toEntity(CustomerCreationRequest request);

    CustomerResponse toResponse(CustomerEntity entity);
    void updateCustomer(@MappingTarget CustomerEntity entity, CustomerUpdateRequest request);
}
