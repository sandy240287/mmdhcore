package com.apm.models.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the "app_health" database table.
 * 
 */
@Entity(name = "app_health")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appHealthId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppHealth extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_HEALTH_SEQ")
	@SequenceGenerator(name = "APP_HEALTH_SEQ", sequenceName = "APP_HEALTH_SEQ", allocationSize = 1)
	@Column(name = "app_health_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appHealthId;

	@Column(name = "ops_sustainability")
	@JsonView(JSONView.ParentObject.class)
	private String opsSustainability;

	@Column(name = "major_issues")
	@JsonView(JSONView.ParentObject.class)
	private String majorIssues;
	
	@Column(name = "helpdesk_incidents_count")
	@JsonView(JSONView.ParentObject.class)
	private String helpdeskIncidentsCount;

	@Column(name = "opportunities")
	@JsonView(JSONView.ParentObject.class)
	private String opportunities;

	@Column(name = "ease_of_maintenance")
	@JsonView(JSONView.ParentObject.class)
	private String easeOfMaintenance;

	@Column(name = "ease_of_replacement")
	@JsonView(JSONView.ParentObject.class)
	private String easeOfReplacement;

	public Long getAppHealthId() {
		return appHealthId;
	}

	public void setAppHealthId(Long appHealthId) {
		this.appHealthId = appHealthId;
	}

	public String getOpsSustainability() {
		return opsSustainability;
	}

	public void setOpsSustainability(String opsSustainability) {
		this.opsSustainability = opsSustainability;
	}

	public String getMajorIssues() {
		return majorIssues;
	}

	public void setMajorIssues(String majorIssues) {
		this.majorIssues = majorIssues;
	}

	public String getHelpdeskIncidentsCount() {
		return helpdeskIncidentsCount;
	}

	public void setHelpdeskIncidentsCount(String helpdeskIncidentsCount) {
		this.helpdeskIncidentsCount = helpdeskIncidentsCount;
	}

	public String getOpportunities() {
		return opportunities;
	}

	public void setOpportunities(String opportunities) {
		this.opportunities = opportunities;
	}

	public String getEaseOfMaintenance() {
		return easeOfMaintenance;
	}

	public void setEaseOfMaintenance(String easeOfMaintenance) {
		this.easeOfMaintenance = easeOfMaintenance;
	}

	public String getEaseOfReplacement() {
		return easeOfReplacement;
	}

	public void setEaseOfReplacement(String easeOfReplacement) {
		this.easeOfReplacement = easeOfReplacement;
	}

	@ManyToOne
	@JoinColumn(name = "application_id")
	private Application application;

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}