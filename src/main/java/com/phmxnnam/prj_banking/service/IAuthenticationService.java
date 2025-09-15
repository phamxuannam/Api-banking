package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.dto.request.AuthenticationRequest;
import com.phmxnnam.prj_banking.dto.response.AuthenticationResponse;
import com.phmxnnam.prj_banking.entity.UserEntity;

import java.util.List;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(UserEntity user);
    String getRole(String username);

}
