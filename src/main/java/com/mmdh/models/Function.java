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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.models.application.Application;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "function" database table.
 * 
 */
@Entity(name = "function")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "functionId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Function extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -1628216357001425688L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCTION_SEQ")
	@SequenceGenerator(name = "FUNCTION_SEQ", sequenceName = "FUNCTION_SEQ", allocationSize = 1)
	@Column(name = "funtion_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long functionId;

	@Column(name = "function_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String functionName;

	public Long getfunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="division_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Division division;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "function", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Process> processes;

	public List<Process> getProcesses() {
		return this.processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
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