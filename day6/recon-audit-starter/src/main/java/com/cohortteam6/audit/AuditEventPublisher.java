package com.cohortteam6.audit;

public class AuditEventPublisher {
    public void publish(String message) {
        System.out.println("[AUDIT] " + message);
    }
}
