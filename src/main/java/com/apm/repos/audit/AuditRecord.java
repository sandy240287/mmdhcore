package com.apm.repos.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity(name = "audit")
public class AuditRecord implements Serializable{

	private static final long serialVersionUID = -193147737946310994L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITRECORD_SEQ")
	@SequenceGenerator(name = "AUDITRECORD_SEQ", sequenceName = "AUDITRECORD_SEQ", allocationSize = 1)
	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "created_by_user")
	@CreatedBy
	private String createdBy;

	@Column(name = "creation_time")
	@CreatedDate
	private Date creationTime;

	@Column(name = "last_modified_by")
	@LastModifiedBy
	private String lastModifiedBy;

	@Column(name = "modification_time")
	@LastModifiedDate
	private Date modificationTime;

	@Column(name = "operation")
	private String operation;
	
	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String currentAuditor) {
		this.lastModifiedBy = currentAuditor;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}