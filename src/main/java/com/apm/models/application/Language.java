package com.apm.models.application;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the "language" database table.
 * 
 */
@Entity(name = "language")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "languageId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Language extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LANGUAGE_SEQ")
	@SequenceGenerator(name = "LANGUAGE_SEQ", sequenceName = "LANGUAGE_SEQ", allocationSize = 1)
	@Column(name = "language_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long languageId;
	
	@Column(name = "langauge_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String languageName;

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