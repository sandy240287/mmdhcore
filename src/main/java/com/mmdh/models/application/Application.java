package com.mmdh.models.application;

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
import com.mmdh.models.Actor;
import com.mmdh.models.Division;
import com.mmdh.models.Function;
import com.mmdh.models.Organization;
import com.mmdh.models.Process;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "application" database table.
 * 
 */
@Entity(name = "application")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "applicationId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Application extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -3123572100446050558L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_SEQ")
	@SequenceGenerator(name = "APPLICATION_SEQ", sequenceName = "APPLICATION_SEQ", allocationSize = 1)
	@Column(name = "application_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long applicationId;

	@Column(name = "name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String name;

	@Column(name = "alias")
	@JsonView(JSONView.ParentObject.class)
	private String alias;

	@Column(name = "description")
	@JsonView(JSONView.ParentObject.class)
	private String description;

	@Column(name = "status")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String status;

	@Column(name = "current_version")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String currentVersion;

	@Column(name = "access_location")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String accessLocation;

	@Column(name = "notes")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private String notes;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getAccessLocation() {
		return accessLocation;
	}

	public void setAccessLocation(String accessLocation) {
		this.accessLocation = accessLocation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organization_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "applications", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Division> divisionsItSupports;

	public List<Division> getDivisionsItSupports() {
		return divisionsItSupports;
	}

	public void setDivisionsItSupports(List<Division> divisionsItSupports) {
		this.divisionsItSupports = divisionsItSupports;
	}

	@ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "applications", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Function> functionsItSupports;

	public List<Function> getFunctionsItSupports() {
		return functionsItSupports;
	}

	public void setFunctionsItSupports(List<Function> functionsItSupports) {
		this.functionsItSupports = functionsItSupports;
	}

	@ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "applications", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Process> processesItSupports;

	public List<Process> getProcessesItSupports() {
		return processesItSupports;
	}

	public void setProcessesItSupports(List<Process> processesItSupports) {
		this.processesItSupports = processesItSupports;
	}

	@ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "applications", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Actor> appActors;

	public List<Actor> getAppActors() {
		return appActors;
	}

	public void setAppActors(List<Actor> appActors) {
		this.appActors = appActors;
	}

	@ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "applications", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<Language> appLanguagesItSupports;

	public List<Language> getAppLanguagesItSupports() {
		return appLanguagesItSupports;
	}

	public void setAppLanguagesItSupports(List<Language> appLanguagesItSupports) {
		this.appLanguagesItSupports = appLanguagesItSupports;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "application", fetch = FetchType.EAGER)
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private List<AppContact> appContacts;

	public List<AppContact> getAppContacts() {
		return this.appContacts;
	}

	public void setAppContacts(List<AppContact> appContacts) {
		this.appContacts = appContacts;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_usage_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppUsage appUsage;

	public AppUsage getAppUsage() {
		return appUsage;
	}

	public void setAppUsage(AppUsage appUsage) {
		this.appUsage = appUsage;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_deployment_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppDeployment appDeployment;

	public AppDeployment getAppDeployment() {
		return appDeployment;
	}

	public void setAppDeployment(AppDeployment appDeployment) {
		this.appDeployment = appDeployment;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_biz_value_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppBizValue appBizValue;

	public AppBizValue getAppBizValue() {
		return appBizValue;
	}

	public void setAppBizValue(AppBizValue appBizValue) {
		this.appBizValue = appBizValue;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_cost_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppCost appCost;

	public AppCost getAppCost() {
		return appCost;
	}

	public void setAppCost(AppCost appCost) {
		this.appCost = appCost;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_health_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppHealth appHealth;

	public AppHealth getAppHealth() {
		return appHealth;
	}

	public void setAppHealth(AppHealth appHealth) {
		this.appHealth = appHealth;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_security_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private AppSecurity appSecurity;

	public AppSecurity getAppSecurity() {
		return appSecurity;
	}

	public void setAppSecurity(AppSecurity appSecurity) {
		this.appSecurity = appSecurity;
	}

}