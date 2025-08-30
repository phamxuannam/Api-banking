package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    String turnOnOffRole(String id);
    String deleteRole(String id);
}
