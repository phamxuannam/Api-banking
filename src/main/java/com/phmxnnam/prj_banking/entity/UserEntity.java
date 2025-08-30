package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class UserEntity extends BaseEntity{

    @Column
    String username;

    @Column
    String password;

    @Column
    int isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_name") )
    Set<RoleEntity> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    CustomerEntity customer;

}

