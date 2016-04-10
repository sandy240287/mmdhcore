package com.apm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm.repos.audit.AuditRecord;

public interface RecordAuditRepository extends JpaRepository<AuditRecord, Long> {

}
