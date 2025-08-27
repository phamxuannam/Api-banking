package com.phmxnnam.prj_banking.dto.request;

import com.phmxnnam.prj_banking.validator.DobValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerCreationRequest {

    @Size(min = 10, message = "NAME_INVALID")
    String fullName;

    @Size(min = 8, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @DobValidator(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    @Email(message = "EMAIL_INVALID")
    String email;

    @Size(min = 10, message = "PHONE_INVALID")
    String phoneNumber;

    String address;
}
