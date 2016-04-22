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
 * The persistent class for the "app_security" database table.
 * 
 */
@Entity(name = "app_security")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appSecurityId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppSecurity extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_SECURITY_SEQ")
	@SequenceGenerator(name = "APP_SECURITY_SEQ", sequenceName = "APP_SECURITY_SEQ", allocationSize = 1)
	@Column(name = "app_security_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appSecurityId;

	@Column(name = "data_sensitivity")
	@JsonView(JSONView.ParentObject.class)
	private String dataSensitivity;

	@Column(name = "sarbanes_oxley")
	@JsonView(JSONView.ParentObject.class)
	private String sarbanesOxley;

	@Column(name = "externally_accessed")
	@JsonView(JSONView.ParentObject.class)
	private Boolean externallyAccessed;

	@Column(name = "externally_managed")
	@JsonView(JSONView.ParentObject.class)
	private Boolean externallyManaged;
	
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