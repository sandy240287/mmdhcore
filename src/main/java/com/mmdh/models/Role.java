package com.mmdh.models;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;


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
	private List<MMDHUser> users;

	public List<MMDHUser> getmmdhUsers() {
		return this.users;
	}

	public void setmmdhUsers(List<MMDHUser> users) {
		this.users = users;
	}

}