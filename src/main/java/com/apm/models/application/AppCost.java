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
 * The persistent class for the "app_cost" database table.
 * 
 */
@Entity(name = "app_cost")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appCostId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppCost extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_COST_SEQ")
	@SequenceGenerator(name = "APP_COST_SEQ", sequenceName = "APP_COST_SEQ", allocationSize = 1)
	@Column(name = "app_cost_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appCostId;

	@Column(name = "annual_cost_of_maintenance")
	@JsonView(JSONView.ParentObject.class)
	private Double annualCostOfMaintenance;

	@Column(name = "one_time_hosting_cost")
	@JsonView(JSONView.ParentObject.class)
	private Double oneTimeHostingCost;
	
	@Column(name = "recurring_hosting_cost")
	@JsonView(JSONView.ParentObject.class)
	private Double recurringHostingCost;

	@Column(name = "software_licenses_one_time_cost")
	@JsonView(JSONView.ParentObject.class)
	private Double softwareLicensesOneTimeCost;

	@Column(name = "software_licenses_recurring_cost")
	@JsonView(JSONView.ParentObject.class)
	private Double softwareLicensesRecurringCost;

	@Column(name = "development_fte_count")
	@JsonView(JSONView.ParentObject.class)
	private Double developmentFTECount;

	@Column(name = "support_fte_count")
	@JsonView(JSONView.ParentObject.class)
	private Double supportFTECount;

	@Column(name = "development_contractors_count")
	@JsonView(JSONView.ParentObject.class)
	private Double developmentContractorsCount;
	
	@Column(name = "support_contractors_count")
	@JsonView(JSONView.ParentObject.class)
	private Double supportContractorsCount;

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