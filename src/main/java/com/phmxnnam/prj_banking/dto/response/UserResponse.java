package com.phmxnnam.prj_banking.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class UserResponse {
    String username;
//    String password;
    String customer_id;
    int status;
    List<RoleResponse> roles;
}
