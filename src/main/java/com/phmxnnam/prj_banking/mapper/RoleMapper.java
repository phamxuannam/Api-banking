package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.dto.response.RoleResponse;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity toEntity(RoleRequest request);
    RoleResponse toResponse(RoleEntity entity);
}
