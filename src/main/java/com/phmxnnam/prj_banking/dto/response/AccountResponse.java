package com.phmxnnam.prj_banking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountResponse {
    String id;
    String accountNumber;
    double balance;
    int status;
    String customer_id;
}
