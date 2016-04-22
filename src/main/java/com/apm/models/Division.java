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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.apm.models.application.Application;
import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the "division" database table.
 * 
 */
@Entity(name = "division")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "divisionId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Division extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -1628216357001425688L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIVISION_SEQ")
	@SequenceGenerator(name = "DIVISION_SEQ", sequenceName = "DIVISION_SEQ", allocationSize = 1)
	@Column(name = "division_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long divisionId;

	@Column(name = "division_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String divisionName;

	public Long getDivisionId() {
		return this.divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return this.divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "division", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Function> functions;

	public List<Function> getFunctions() {
		return this.functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Application> applications;

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

}