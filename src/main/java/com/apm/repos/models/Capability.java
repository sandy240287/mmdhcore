package com.apm.repos.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the "capability" database table.
 * 
 */
@Entity(name = "capability")

public class Capability implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 993179216687278566L;

	@Id
	@Column(name = "cap_id", unique = true, nullable = false)
	private Long capId;

	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "cap_name", nullable = false)
	private String capName;

	public Long geCapId() {
		return this.capId;
	}

	public void setCapId(Long capId) {
		this.capId = capId;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getCapName() {
		return this.capName;
	}

	public void setCapName(String capName) {
		this.capName = capName;
	}
	
}