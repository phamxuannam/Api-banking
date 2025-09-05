package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.PermissionRequest;
import com.phmxnnam.prj_banking.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    PermissionResponse getById(String id);
    String turnOnOffByIf(String id);
    String deleteById(String id);
}
