package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "tokens_invalid")
public class TokenInvalidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String idToken;
    Date expiryTime;
}
