package com.mmdh.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmdh.repos.audit.AuditRecord;

public interface RecordAuditRepository extends JpaRepository<AuditRecord, Long> {

}
