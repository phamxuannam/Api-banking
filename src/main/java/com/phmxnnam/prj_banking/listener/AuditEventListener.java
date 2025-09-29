package com.phmxnnam.prj_banking.listener;

import com.phmxnnam.prj_banking.event.TransactionActionEvent;
import com.phmxnnam.prj_banking.service.IAuditLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuditEventListener {

    IAuditLogService auditLogService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCommitted(TransactionActionEvent event){
        auditLogService.appendFromEvent(event);
    }

}
