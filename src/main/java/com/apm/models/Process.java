package com.apm.models;

import java.io.Serializable;
import java.util.List;

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
 * The persistent class for the "process" database table.
 * 
 */
@Entity(name = "process")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "processId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Process extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -1628216357001425688L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCESS_SEQ")
	@SequenceGenerator(name = "PROCESS_SEQ", sequenceName = "PROCESS_SEQ", allocationSize = 1)
	@Column(name = "process_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long processId;

	@Column(name = "process_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String processName;

	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="function_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Function function;
	
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
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