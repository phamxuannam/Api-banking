package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CustomerEntity extends BaseEntity {

    @Column
    String fullName;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    LocalDate dob;

    @Column
    String email;

    @Column
    String phoneNumber;

    @Column
    String address;

    @Column
    int isActive;
}
