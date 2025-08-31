package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CustomerEntity extends BaseEntity {

    @Column
    String identification;

    @Column
    String fullName;

    @Column(nullable = false)
    LocalDate dob;

    @Column
    String email;

    @Column
    String phoneNumber;

    @Column
    String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserEntity> users;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<AccountEntity> accounts;
}
