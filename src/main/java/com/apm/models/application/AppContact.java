package com.apm.models.application;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the "app_contact" database table.
 * 
 */
@Entity(name = "app_contact")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appContactId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AppContact extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 4120053288541244787L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_CONTACT_SEQ")
	@SequenceGenerator(name = "APP_CONTACT_SEQ", sequenceName = "APP_CONTACT_SEQ", allocationSize = 1)
	@Column(name = "app_contact_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long appContactId;

	@Column(name = "contact_type", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String contactType;

	@Column(name = "contact_type_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String contactTypeName;

	public Long getAppContactId() {
		return appContactId;
	}

	public void setAppContactId(Long appContactId) {
		this.appContactId = appContactId;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactTypeName() {
		return contactTypeName;
	}

	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
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

	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Contact contact;

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}