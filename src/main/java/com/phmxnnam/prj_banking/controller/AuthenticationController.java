package com.phmxnnam.prj_banking.controller;

import com.nimbusds.jose.JOSEException;
import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.AuthenticationRequest;
import com.phmxnnam.prj_banking.dto.request.IntrospectRequest;
import com.phmxnnam.prj_banking.dto.request.LogoutRequest;
import com.phmxnnam.prj_banking.dto.response.AuthenticationResponse;
import com.phmxnnam.prj_banking.dto.response.IntrospectResponse;
import com.phmxnnam.prj_banking.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("OK")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .message("OK")
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(authenticationService.logout(request))
                .build();
    }

}
