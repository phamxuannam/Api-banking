package com.phmxnnam.prj_banking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse<T> {
    int code;
    String message;
    T result;
}
