package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "permissions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionEntity {

    @Id
    String name;

    @Column
    String description;

    @Column
    int isActive;

    @ManyToMany(mappedBy = "permissions")
    Set<RoleEntity> roles;

}
