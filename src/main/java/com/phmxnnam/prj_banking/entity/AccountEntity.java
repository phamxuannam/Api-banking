package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountEntity extends BaseEntity {

    @Column
    String accountNumber;

    @Column
    double balance;

    @Column
    int isActive;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    CustomerEntity customer;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    Set<TransactionEntity> transactionFrom;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    Set<TransactionEntity> transactionTo;

}
