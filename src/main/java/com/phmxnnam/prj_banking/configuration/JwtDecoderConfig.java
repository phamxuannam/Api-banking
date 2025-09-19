package com.phmxnnam.prj_banking.configuration;

import com.nimbusds.jose.JOSEException;
import com.phmxnnam.prj_banking.dto.request.IntrospectRequest;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.service.IAuthenticationService;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Configuration
public class JwtDecoderConfig implements JwtDecoder {

    @Autowired
    private IAuthenticationService authenticationService;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
           var response = authenticationService.introspect(IntrospectRequest.builder().token(token).build());
           boolean isValid = response.isValid();
           if(!isValid) throw new AppException(ErrorCode.TOKEN_INVALID);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKey = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
