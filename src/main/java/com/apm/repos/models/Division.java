package com.apm.repos.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the "division" database table.
 * 
 */
@Entity(name = "division")

public class Division implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1628216357001425688L;

	@Id
	@Column(name = "div_id", unique = true, nullable = false)
	private Long divId;

	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "div_name", nullable = false)
	private String divName;

	public Long geDivId() {
		return this.divId;
	}

	public void setDivId(Long divId) {
		this.divId = divId;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getDivName() {
		return this.divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}
	
	@OneToMany(targetEntity=Role.class)
	@JoinTable(name="div_role", joinColumns = @JoinColumn( name="div_id"), inverseJoinColumns = @JoinColumn( name="role_id"))
	@CollectionId(columns=@Column(name="div_role_id"), type=@Type(type="long"), generator = "native")
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(targetEntity=Capability.class)
	@JoinTable(name="div_capability", joinColumns = @JoinColumn( name="div_id"), inverseJoinColumns = @JoinColumn( name="cap_id"))
	@CollectionId(columns=@Column(name="div_capability_id"), type=@Type(type="long"), generator = "native")
	private List<Capability> capabilities;

	public List<Capability> getCapabilities() {
		return this.capabilities;
	}

	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
	}
	
}