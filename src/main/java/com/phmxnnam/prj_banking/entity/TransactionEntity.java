package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_accountId")
    AccountEntity fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_accountId")
    AccountEntity toAccount;

    @Column
    String fromAccountNumber;

    @Column
    String toAccountNumber;

    @Column(nullable = false)
    double amount;

    @Column(nullable = false)
    String transactionType;

    @Column
    String description;

    @Column
    double balancerBefore;

    @Column
    double balancerAfter;

    @Column
    String status;
}
