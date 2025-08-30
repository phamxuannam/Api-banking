package com.phmxnnam.prj_banking.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserUpdateRequest {

    @Size(min = 10, message = " PASSWORD_INVALID")
    String password;
}
