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

    String fromAccount;

    String toAccount;

    double amount;

    String transactionType;
}
