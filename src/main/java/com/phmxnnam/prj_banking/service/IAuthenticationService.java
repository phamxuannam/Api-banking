package com.phmxnnam.prj_banking.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.phmxnnam.prj_banking.dto.request.AuthenticationRequest;
import com.phmxnnam.prj_banking.dto.request.IntrospectRequest;
import com.phmxnnam.prj_banking.dto.request.LogoutRequest;
import com.phmxnnam.prj_banking.dto.response.AuthenticationResponse;
import com.phmxnnam.prj_banking.dto.response.IntrospectResponse;
import com.phmxnnam.prj_banking.entity.UserEntity;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(UserEntity user);
    String getRole(String username);
    String logout(LogoutRequest request) throws ParseException, JOSEException;
    SignedJWT verifyToken(String token, boolean refresh) throws JOSEException, ParseException;
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

}
