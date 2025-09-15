package com.phmxnnam.prj_banking.service.implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.phmxnnam.prj_banking.dto.request.AuthenticationRequest;
import com.phmxnnam.prj_banking.dto.response.AuthenticationResponse;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.repository.RoleRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserRepository userRepository;

    @Value("${jwt.SIGNER_KEY}")
    String SIGNER_KEY;


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
                .issuer("xnp")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
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
                stringJoiner.add("Role_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        }
        return stringJoiner.toString();
    }
}
