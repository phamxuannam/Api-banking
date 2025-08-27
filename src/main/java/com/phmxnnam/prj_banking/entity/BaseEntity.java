package com.phmxnnam.prj_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDate createdAt;

    @Column(name = "created_by")
    @CreatedBy
    String createdBy;

    @Column(name = "modified_at")
    @LastModifiedDate
    LocalDate modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    String modifiedBy;
}
