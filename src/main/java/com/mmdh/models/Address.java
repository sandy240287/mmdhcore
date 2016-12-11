package com.mmdh.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "address" database table.
 * 
 */
@Entity(name = "address")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "addressId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Address extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 5501362362479548174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
	@SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", allocationSize = 1)
	@Column(name = "address_id", unique = true, nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long addressId;
	
	@Column(name = "street")
	@JsonView(JSONView.ParentObject.class)
	private String street;

	@Column(name = "city")
	@JsonView(JSONView.ParentObject.class)
	private String city;

	@Column(name = "state")
	@JsonView(JSONView.ParentObject.class)
	private String state;

	@Column(name = "zip")
	@JsonView(JSONView.ParentObject.class)
	private String zip;

	@Column(name = "website")
	@JsonView(JSONView.ParentObject.class)
	private String website;

	@Column(name = "social_link")
	@JsonView(JSONView.ParentObject.class)
	private String socialLink;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSocialLink() {
		return socialLink;
	}

	public void setSocialLink(String socialLink) {
		this.socialLink = socialLink;
	}

}