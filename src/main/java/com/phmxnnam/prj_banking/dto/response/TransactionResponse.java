package com.phmxnnam.prj_banking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionResponse {
    String fromAccount;
    String toAccount;
    double amount;
    String transactionType;
    double balancerBefore;
    double balancerAfter;
    String status;
}
