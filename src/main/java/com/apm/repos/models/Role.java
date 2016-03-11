package com.apm.repos.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;


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

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@OneToMany(targetEntity=APMUser.class, cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="role_user", joinColumns = @JoinColumn( name="role_id"), inverseJoinColumns = @JoinColumn( name="user_id"))
	@CollectionId(columns=@Column(name="role_user_id"), type=@Type(type="long"), generator = "native")
	private List<APMUser> users;

	public List<APMUser> getAPMUsers() {
		return this.users;
	}

	public void setAPMUsers(List<APMUser> users) {
		this.users = users;
	}

}