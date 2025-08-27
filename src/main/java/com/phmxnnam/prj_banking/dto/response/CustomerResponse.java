package com.phmxnnam.prj_banking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerResponse {
    String id;
    String fullName;
    String username;
    String email;
    String phoneNumber;
    String address;
}
