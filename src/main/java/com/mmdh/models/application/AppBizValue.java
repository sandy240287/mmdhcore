package com.mmdh.models.application;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "app_deployment" database table.
 * 
 */
@Entity(name = "app_bizvalue")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appBizValueId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppBizValue extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_BIZVALUE_SEQ")
	@SequenceGenerator(name = "APP_BIZVALUE_SEQ", sequenceName = "APP_BIZVALUE_SEQ", allocationSize = 1)
	@Column(name = "app_bizvalue_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appBizValueId;

	@Column(name = "biz_criticality")
	@JsonView(JSONView.ParentObject.class)
	private Long bizCriticality;

	@Column(name = "competitive_advantage")
	@JsonView(JSONView.ParentObject.class)
	private Long competitiveAdvantage;
	
	@Column(name = "current_effectiveness")
	@JsonView(JSONView.ParentObject.class)
	private Long currentEffectiveness;

	@Column(name = "breadth_of_usage")
	@JsonView(JSONView.ParentObject.class)
	private Long breadthOfUsage;

	@Column(name = "functional_condition")
	@JsonView(JSONView.ParentObject.class)
	private Long functionalCondition;

	@Column(name = "technical_condition")
	@JsonView(JSONView.ParentObject.class)
	private Long technicalCondition;

	public Long getAppBizValueId() {
		return appBizValueId;
	}

	public void setAppBizValueId(Long appBizValueId) {
		this.appBizValueId = appBizValueId;
	}

	public Long getBizCriticality() {
		return bizCriticality;
	}

	public void setBizCriticality(Long bizCriticality) {
		this.bizCriticality = bizCriticality;
	}

	public Long getCompetitiveAdvantage() {
		return competitiveAdvantage;
	}

	public void setCompetitiveAdvantage(Long competitiveAdvantage) {
		this.competitiveAdvantage = competitiveAdvantage;
	}

	public Long getCurrentEffectiveness() {
		return currentEffectiveness;
	}

	public void setCurrentEffectiveness(Long currentEffectiveness) {
		this.currentEffectiveness = currentEffectiveness;
	}

	public Long getBreadthOfUsage() {
		return breadthOfUsage;
	}

	public void setBreadthOfUsage(Long breadthOfUsage) {
		this.breadthOfUsage = breadthOfUsage;
	}

	public Long getFunctionalCondition() {
		return functionalCondition;
	}

	public void setFunctionalCondition(Long functionalCondition) {
		this.functionalCondition = functionalCondition;
	}

	public Long getTechnicalCondition() {
		return technicalCondition;
	}

	public void setTechnicalCondition(Long technicalCondition) {
		this.technicalCondition = technicalCondition;
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