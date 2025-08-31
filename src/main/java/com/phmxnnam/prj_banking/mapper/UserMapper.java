package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.request.UserUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;
import com.phmxnnam.prj_banking.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "customer", ignore = true)
    UserEntity toEntity(UserCreationRequest request);

    @Mapping(source = "customer.id", target = "customer_id")
    @Mapping(source = "isActive",target = "status")
    UserResponse toResponse(UserEntity entity);

    void updateUser(@MappingTarget UserEntity entity, UserUpdateRequest request);
}
