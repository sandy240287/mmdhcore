package com.apm.repos.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the "organization" database table.
 * 
 */
@Entity(name = "organization")

public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8640502776331958667L;

	@Id
	@Column(name = "org_id", unique = true, nullable = false)
	private Long orgId;

	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "org_name", nullable = false)
	private String orgName;

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/* commenting as Org will never have Roles without a Division
	@OneToMany(targetEntity = Role.class, cascade=CascadeType.ALL)
	@JoinTable(name = "org_role", joinColumns = @JoinColumn(name = "org_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@CollectionId(columns=@Column(name="org_role_id"), type=@Type(type="long"), generator = "native")
	private List<Role> roleByOrgId;

	public List<Role> getRoleByOrgId() {
		return roleByOrgId;
	}

	public void setRoleByOrgId(List<Role> roleByOrgId) {
		this.roleByOrgId = roleByOrgId;
	}
	 */
	@OneToMany(targetEntity = Division.class, cascade=CascadeType.ALL)
	@JoinTable(name = "org_division", joinColumns = @JoinColumn(name = "org_id"), inverseJoinColumns = @JoinColumn(name = "div_id"))
	@CollectionId(columns=@Column(name="org_division_id"), type=@Type(type="long"), generator = "native")
	private List<Division> divisions;

	public List<Division> getDivisions() {
		return this.divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}
	
}