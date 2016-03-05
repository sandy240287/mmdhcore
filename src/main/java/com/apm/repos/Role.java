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
	@Column(name="role_id", unique=true, nullable=false)
	private Long roleId;

	@Column(name="audit_id", nullable=false)
	private Long auditId;

	@Column(name="is_active", nullable=false)
	private Boolean isActive;

	@Column(name="role_name", nullable=false)
	private String roleName;
	
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}