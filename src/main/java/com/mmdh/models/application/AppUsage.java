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
 * The persistent class for the "app_usage" database table.
 * 
 */
@Entity(name = "app_usage")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appUsageId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppUsage extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_USAGE_SEQ")
	@SequenceGenerator(name = "APP_USAGE_SEQ", sequenceName = "APP_USAGE_SEQ", allocationSize = 1)
	@Column(name = "app_usage_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appUsageId;

	@Column(name = "users_count")
	@JsonView(JSONView.ParentObject.class)
	private long usersCount;

	@Column(name = "concurrent_users_count")
	@JsonView(JSONView.ParentObject.class)
	private long concurrentUsersCount;

	@Column(name = "transactions_count")
	@JsonView(JSONView.ParentObject.class)
	private long transactionsCount;

	@Column(name = "region")
	@JsonView(JSONView.ParentObject.class)
	private String region;

	@Column(name = "availabilty")
	@JsonView(JSONView.ParentObject.class)
	private String availabilty;

	@Column(name = "scalabilty")
	@JsonView(JSONView.ParentObject.class)
	private String scalabilty;

	public Long getAppUsageId() {
		return appUsageId;
	}

	public void setAppUsageId(Long appUsageId) {
		this.appUsageId = appUsageId;
	}
	
	public long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}

	public long getConcurrentUsersCount() {
		return concurrentUsersCount;
	}

	public void setConcurrentUsersCount(long concurrentUsersCount) {
		this.concurrentUsersCount = concurrentUsersCount;
	}

	public long getTransactionsCount() {
		return transactionsCount;
	}

	public void setTransactionsCount(long transactionsCount) {
		this.transactionsCount = transactionsCount;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAvailabilty() {
		return availabilty;
	}

	public void setAvailabilty(String availabilty) {
		this.availabilty = availabilty;
	}

	public String getScalabilty() {
		return scalabilty;
	}

	public void setScalabilty(String scalabilty) {
		this.scalabilty = scalabilty;
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