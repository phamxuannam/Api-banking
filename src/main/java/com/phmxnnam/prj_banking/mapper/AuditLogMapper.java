package com.phmxnnam.prj_banking.mapper;

import com.phmxnnam.prj_banking.entity.AuditLogEntity;
import com.phmxnnam.prj_banking.event.TransactionActionEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    AuditLogEntity toEntity(TransactionActionEvent event);
}
