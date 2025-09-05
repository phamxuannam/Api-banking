package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
public class RoleEntity{
    @Id
    String name;

    @Column
    String des;

    @Column
    int isActive;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    Set<UserEntity> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "roles_permissions", joinColumns = @JoinColumn(name = "role_name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name") )
    Set<PermissionEntity> permissions;

}
