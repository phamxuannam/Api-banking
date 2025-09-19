package com.phmxnnam.prj_banking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class IntrospectRequest {
    String token;
}
