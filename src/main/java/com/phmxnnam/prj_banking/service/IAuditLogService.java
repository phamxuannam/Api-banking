package com.phmxnnam.prj_banking.service;

import com.phmxnnam.prj_banking.event.TransactionActionEvent;

public interface IAuditLogService {
    void appendFromEvent(TransactionActionEvent event);
    void appendFailure(TransactionActionEvent event);
}
