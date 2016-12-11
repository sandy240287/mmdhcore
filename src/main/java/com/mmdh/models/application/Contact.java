package com.mmdh.models.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "contact" database table.
 * 
 */
@Entity(name = "contact")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "contactId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Contact extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_SEQ")
	@SequenceGenerator(name = "CONTACT_SEQ", sequenceName = "CONTACT_SEQ", allocationSize = 1)
	@Column(name = "contact_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long contactId;
	
	@Column(name = "first_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String lastName;

	@Column(name = "middle_name")
	@JsonView(JSONView.ParentObject.class)
	private String middleName;

	@Column(name = "o_phone")
	@JsonView(JSONView.ParentObject.class)
	private String oPhone;

	@Column(name = "m_phone")
	@JsonView(JSONView.ParentObject.class)
	private String mPhone;

	@Column(name = "email")
	@JsonView(JSONView.ParentObject.class)
	private String email;

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getoPhone() {
		return oPhone;
	}

	public void setoPhone(String oPhone) {
		this.oPhone = oPhone;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToOne
	@JoinColumn(name = "app_contact_id")
	private AppContact appContact;

	public AppContact getAppContact() {
		return appContact;
	}

	public void setAppContact(AppContact appContact) {
		this.appContact = appContact;
	}

}