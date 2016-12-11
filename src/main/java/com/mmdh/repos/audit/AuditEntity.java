package com.mmdh.repos.audit;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
@MappedSuperclass
public class AuditEntity {
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "audit_id")
	@JsonIgnore
	private AuditRecord auditRecord;

	public AuditRecord getAuditRecord() {
		return auditRecord;
	}

	public void setAuditRecord(AuditRecord auditRecord) {
		this.auditRecord = auditRecord;
	}
	
}