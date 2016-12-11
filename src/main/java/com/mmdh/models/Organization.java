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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "organization" database table.
 * 
 */
@Entity(name = "organization")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "organizationId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Organization extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -8640502776331958667L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_SEQ")
	@SequenceGenerator(name = "ORGANIZATION_SEQ", sequenceName = "ORGANIZATION_SEQ", allocationSize = 1)
	@Column(name = "organization_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long organizationId;

	@Column(name = "name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String name;
	
	@Column(name = "alias")
	@JsonView(JSONView.ParentObject.class)
	private String alias;

	@Column(name = "description")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String description;

	@Column(name = "phone1")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String phone1;

	@Column(name = "phone2")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String phone2;

	@Column(name = "email")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String email;

	@Column(name = "website")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String website;

	public List<MMDHUser> getUsers() {
		return users;
	}

	public void setUsers(List<MMDHUser> users) {
		this.users = users;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<MMDHUser> users;

	public List<MMDHUser> getmmdhUsers() {
		return this.users;
	}

	public void setmmdhUsers(List<MMDHUser> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Actor> actors;

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "organization", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Division> divisions;

	public List<Division> getDivisions() {
		return this.divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}
	
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}