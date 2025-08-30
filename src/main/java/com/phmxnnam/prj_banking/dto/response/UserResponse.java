package com.phmxnnam.prj_banking.dto.response;

import com.phmxnnam.prj_banking.entity.CustomerEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserResponse {
    String username;
    String password;
    String customer_id;
    List<RoleResponse> roles;
}
