package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.dto.request.PermissionRequest;
import com.phmxnnam.prj_banking.dto.response.PermissionResponse;
import com.phmxnnam.prj_banking.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toEntity(PermissionRequest request);
    PermissionResponse toResponse(PermissionEntity entity);
}
