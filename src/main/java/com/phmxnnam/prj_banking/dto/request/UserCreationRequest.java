package com.phmxnnam.prj_banking.dto.request;

import com.phmxnnam.prj_banking.entity.CustomerEntity;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserCreationRequest {

    @Size(min = 10, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String customer_id;
}
