package com.phmxnnam.prj_banking.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRequest {
    @Size(min = 10, max = 10, message = "Phone Number must be at least 10 numbers")
    String accountNumber;

    @Size(min = 50, message = "must be at least 50 thousand VND.")
    double balance;

    String customer_id;
}
