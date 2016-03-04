package com.apm.repos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The persistent class for the "User" database table.
 * 
 */
@Entity(name = "apmuser")

public class APMUser implements Serializable {

	private static final long serialVersionUID = 7367320386207788056L;

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "\"Audit_id\"")
	private Long audit_id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name")
	private Long lastName;

	@Column(name = "middle_name")
	private Long middleName;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAudit_id() {
		return this.audit_id;
	}

	public void setAudit_id(Long audit_id) {
		this.audit_id = audit_id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getLastName() {
		return this.lastName;
	}

	public void setLastName(Long lastName) {
		this.lastName = lastName;
	}

	public Long getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(Long middleName) {
		this.middleName = middleName;
	}

	@OneToMany(targetEntity=Role.class)
	private List<Role> roles;

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	private PasswordProfile passwordProfile;

	public PasswordProfile getPasswordProfile() {
		return this.passwordProfile;
	}

	public void setPasswordProfile(PasswordProfile passwordProfile) {
		this.passwordProfile = passwordProfile;
	}

}