package com.phmxnnam.prj_banking.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionRequest {
    @Size(min = 10, message = "ACCOUNT_NOT_EXISTS")
    String fromAccount;

    @Size(min = 10, message = "ACCOUNT_NOT_EXISTS")
    String toAccount;

    @Size(min = 5, message = "must be at least 5 thousand VND")
    double amount;

    String transactionType;

    @Size(min = 0)
    double balancerBefore;

    @Size(min = 0)
    double balancerAfter;
}
