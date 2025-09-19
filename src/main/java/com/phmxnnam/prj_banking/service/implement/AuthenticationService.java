package com.phmxnnam.prj_banking.service.implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phmxnnam.prj_banking.dto.request.AuthenticationRequest;
import com.phmxnnam.prj_banking.dto.request.IntrospectRequest;
import com.phmxnnam.prj_banking.dto.request.LogoutRequest;
import com.phmxnnam.prj_banking.dto.response.AuthenticationResponse;
import com.phmxnnam.prj_banking.dto.response.IntrospectResponse;
import com.phmxnnam.prj_banking.entity.TokenInvalidEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.repository.TokenInvalidRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    PasswordEncoder passwordEncoder;
    TokenInvalidRepository tokenInvalidRepository;
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.VALID-DURATION}")
    long VALID_DURATION;

    @NonFinal
    @Value("${jwt.REFRESHABLE-DURATION}")
    long REFRESHABLE_DURATION;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow( () ->
                new AppException(ErrorCode.USER_NOT_EXISTS) );
        boolean checkPass = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!checkPass) throw new AppException(ErrorCode.PASSWORD_INVALID);
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .valid(true)
                .build();
    }

    @Override
    public String generateToken(UserEntity user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .issuer("xnp")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("scope", getRole(user.getUsername()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());


        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRole(String username) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        }
        return stringJoiner.toString();
    }

    @Override
    public SignedJWT verifyToken(String token, boolean refresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedToken = SignedJWT.parse(token);
        boolean verified = signedToken.verify(verifier);
        Date expTime = (refresh)
            ? new Date(signedToken.getJWTClaimsSet().getExpirationTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
            : signedToken.getJWTClaimsSet().getExpirationTime();
        String idToken = signedToken.getJWTClaimsSet().getJWTID();

        if(!(verified && expTime.after(new Date())))
            throw new AppException(ErrorCode.TOKEN_INVALID);

        if(tokenInvalidRepository.existsByIdToken(idToken)) throw new AppException(ErrorCode.TOKEN_INVALID);

        return signedToken;
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        boolean valid = true;
        try {
            verifyToken(token,false);
        } catch(Exception e) {
            valid = false;
        }
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    @Override
    public String logout(LogoutRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        SignedJWT signedToken = verifyToken(token,true);
        String idToken = signedToken.getJWTClaimsSet().getJWTID();
        Date expTime = new Date(signedToken.getJWTClaimsSet().getExpirationTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli());
        TokenInvalidEntity tokenInvalid = TokenInvalidEntity.builder()
                .idToken(idToken)
                .expiryTime(expTime)
                .build();
        tokenInvalidRepository.save(tokenInvalid);
        return "logout successfully.";
    }

}
