package com.phmxnnam.prj_banking.dto.request;

import com.phmxnnam.prj_banking.entity.RoleEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PermissionRequest {
    String name;
    String description;
}
