package com.apm.repos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The persistent class for the "Role" database table.
 * 
 */
@Entity(name = "role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = -1096205185867751910L;

	@Id
	@Column(name="\"Role_Id\"", unique=true, nullable=false)
	private Long role_Id;

	@Column(name="\"Audit_id\"", nullable=false)
	private Long audit_id;

	@Column(name="is_active", nullable=false)
	private Boolean isActive;

	@Column(name="\"Role_name\"", nullable=false)
	private String role_name;
	
	public Long getRole_Id() {
		return this.role_Id;
	}

	public void setRole_Id(Long role_Id) {
		this.role_Id = role_Id;
	}

	public Long getAudit_id() {
		return this.audit_id;
	}

	public void setAudit_id(Long audit_id) {
		this.audit_id = audit_id;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRole_name() {
		return this.role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}