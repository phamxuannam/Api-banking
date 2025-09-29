package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.entity.AuditLogEntity;
import com.phmxnnam.prj_banking.event.TransactionActionEvent;
import com.phmxnnam.prj_banking.mapper.AuditLogMapper;
import com.phmxnnam.prj_banking.repository.AuditLogRepository;
import com.phmxnnam.prj_banking.service.IAuditLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuditLogService implements IAuditLogService {

    AuditLogRepository auditLogRepository;
    AuditLogMapper auditLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void appendFromEvent(TransactionActionEvent event) {
        AuditLogEntity entity = auditLogMapper.toEntity(event);
        auditLogRepository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void appendFailure(TransactionActionEvent event) {
        appendFromEvent(event);
    }

}
