package com.apm.repos.models;

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
 * The persistent class for the "organization" database table.
 * 
 */
@Entity(name = "organization")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "organizationId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -8640502776331958667L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_SEQ")
	@SequenceGenerator(name = "ORGANIZATION_SEQ", sequenceName = "ORGANIZATION_SEQ", allocationSize = 1)
	@Column(name = "organization_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long organizationId;

	@Column(name = "organization_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String organizationName;

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<APMUser> users;

	public List<APMUser> getAPMUsers() {
		return this.users;
	}

	public void setAPMUsers(List<APMUser> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Actor> actors;

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Division> divisions;

	public List<Division> getDivisions() {
		return this.divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}

}