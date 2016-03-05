package com.apm.repos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;
	
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@OneToMany(targetEntity=Role.class)
	@JoinTable(name="user_role", joinColumns = @JoinColumn( name="user_id"), inverseJoinColumns = @JoinColumn( name="role_id"))
	private List<Role> roles;

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	// commented for time being. If fully loaded user object is required, just uncomment it. 
	/*
	@OneToOne(targetEntity=PasswordProfile.class) 
	@JoinColumn(name="user_id")
	private PasswordProfile passwordProfile;

	public PasswordProfile getPasswordProfile() {
		return this.passwordProfile;
	}

	public void setPasswordProfile(PasswordProfile passwordProfile) {
		this.passwordProfile = passwordProfile;
	}
	*/
}