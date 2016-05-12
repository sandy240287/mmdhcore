package com.apm.models.application;

import java.io.Serializable;
import java.sql.Date;

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
 * The persistent class for the "app_deployment" database table.
 * 
 */
@Entity(name = "app_deployment")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appDeploymentId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppDeployment extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_DEPLOYMENT_SEQ")
	@SequenceGenerator(name = "APP_DEPLOYMENT_SEQ", sequenceName = "APP_DEPLOYMENT_SEQ", allocationSize = 1)
	@Column(name = "app_deployment_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appDeploymentId;

	@Column(name = "originally_developed_at")
	@JsonView(JSONView.ParentObject.class)
	private String originallyDevelopedAt;

	@Column(name = "original_deployment_date")
	@JsonView(JSONView.ParentObject.class)
	private Date originalDeploymentDate;

	@Column(name = "last_deployment_date")
	@JsonView(JSONView.ParentObject.class)
	private Date lastDeploymentDate;

	@Column(name = "managed_type")
	@JsonView(JSONView.ParentObject.class)
	private String managedType;

	@Column(name = "interfaces_count_in")
	@JsonView(JSONView.ParentObject.class)
	private String interfacesCountIN;

	@Column(name = "interfaces_count_out")
	@JsonView(JSONView.ParentObject.class)
	private String interfacesCountOUT;

	@Column(name = "software_platform_type")
	@JsonView(JSONView.ParentObject.class)
	private String softwarePlatformType;

	@Column(name = "database_platform")
	@JsonView(JSONView.ParentObject.class)
	private String databasePlatform;

	@Column(name = "hardware_platform")
	@JsonView(JSONView.ParentObject.class)
	private String hardwarePlatform;

	@Column(name = "organization_asset_id")
	@JsonView(JSONView.ParentObject.class)
	private String organizationAssetId;

	@Column(name = "recovery_point_obj")
	@JsonView(JSONView.ParentObject.class)
	private String recoveryPointObj;

	@Column(name = "recovery_time_obj")
	@JsonView(JSONView.ParentObject.class)
	private String recoveryTimeObj;

	//Programming Language(s)
	
	public Long getAppDeploymentId() {
		return appDeploymentId;
	}

	public void setAppDeploymentId(Long appDeploymentId) {
		this.appDeploymentId = appDeploymentId;
	}

	public String getOriginallyDevelopedAt() {
		return originallyDevelopedAt;
	}

	public void setOriginallyDevelopedAt(String originallyDevelopedAt) {
		this.originallyDevelopedAt = originallyDevelopedAt;
	}

	public Date getOriginalDeploymentDate() {
		return originalDeploymentDate;
	}

	public void setOriginalDeploymentDate(Date originalDeploymentDate) {
		this.originalDeploymentDate = originalDeploymentDate;
	}

	public Date getLastDeploymentDate() {
		return lastDeploymentDate;
	}

	public void setLastDeploymentDate(Date lastDeploymentDate) {
		this.lastDeploymentDate = lastDeploymentDate;
	}

	public String getManagedType() {
		return managedType;
	}

	public void setManagedType(String managedType) {
		this.managedType = managedType;
	}

	public String getInterfacesCountIN() {
		return interfacesCountIN;
	}

	public void setInterfacesCountIN(String interfacesCountIN) {
		this.interfacesCountIN = interfacesCountIN;
	}

	public String getInterfacesCountOUT() {
		return interfacesCountOUT;
	}

	public void setInterfacesCountOUT(String interfacesCountOUT) {
		this.interfacesCountOUT = interfacesCountOUT;
	}

	public String getSoftwarePlatformType() {
		return softwarePlatformType;
	}

	public void setSoftwarePlatformType(String softwarePlatformType) {
		this.softwarePlatformType = softwarePlatformType;
	}

	public String getDatabasePlatform() {
		return databasePlatform;
	}

	public void setDatabasePlatform(String databasePlatform) {
		this.databasePlatform = databasePlatform;
	}

	public String getHardwarePlatform() {
		return hardwarePlatform;
	}

	public void setHardwarePlatform(String hardwarePlatform) {
		this.hardwarePlatform = hardwarePlatform;
	}

	public String getOrganizationAssetId() {
		return organizationAssetId;
	}

	public void setOrganizationAssetId(String organizationAssetId) {
		this.organizationAssetId = organizationAssetId;
	}

	public String getRecoveryPointObj() {
		return recoveryPointObj;
	}

	public void setRecoveryPointObj(String recoveryPointObj) {
		this.recoveryPointObj = recoveryPointObj;
	}

	public String getRecoveryTimeObj() {
		return recoveryTimeObj;
	}

	public void setRecoveryTimeObj(String recoveryTimeObj) {
		this.recoveryTimeObj = recoveryTimeObj;
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