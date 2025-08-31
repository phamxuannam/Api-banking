package com.phmxnnam.prj_banking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerResponse {
    String id;
    String identification;
    String fullName;
    String email;
    String phoneNumber;
    String address;
    List<UserResponse> users;
    List<AccountResponse> accounts;
}
