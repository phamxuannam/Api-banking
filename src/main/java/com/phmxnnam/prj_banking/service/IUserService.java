package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.request.UserUpdateRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse create(UserCreationRequest request);
    List<UserResponse> getAll();
    UserResponse getById(String id);
    UserResponse changePassword(UserUpdateRequest request, String id);
    String turnOnOfUserById(String id);
    String deleteUserById(String id);
}
