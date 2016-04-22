package com.apm.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the "Role" database table.
 * 
 */
@Entity(name = "role")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="roleId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Role extends AuditEntity implements Serializable {
	
	private static final long serialVersionUID = -1096205185867751910L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
	@SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
	@Column(name="role_id", unique=true, nullable=false)
	@JsonView(JSONView.ParentObject.class)
	private Long roleId;

	@Column(name="role_name", nullable=false)
	@JsonView(JSONView.ParentObject.class)
	private String roleName;
	
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@OneToMany(mappedBy="role", cascade= CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<APMUser> users;

	public List<APMUser> getAPMUsers() {
		return this.users;
	}

	public void setAPMUsers(List<APMUser> users) {
		this.users = users;
	}

}