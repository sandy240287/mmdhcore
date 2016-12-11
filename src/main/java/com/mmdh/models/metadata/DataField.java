package com.mmdh.models.metadata;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmdh.models.Organization;
import com.mmdh.repos.audit.AuditEntity;
import com.mmdh.repos.audit.AuditEntityListener;
import com.mmdh.utils.JSONView;

/**
 * The persistent class for the "data_field" database table.
 * 
 */
@Entity(name = "data_field")
//@IdClass(DataFieldPK.class)
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dataFieldId")
public class DataField extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -8911616369331953134L;

	@Id
	@Column(name = "data_field_id", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private Long dataFieldId;

	@Column(name = "name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String name;

	@Column(name = "display_name", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String displayName;

	@Column(name = "locale", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String locale;

	public Long getDataFieldId() {
		return dataFieldId;
	}

	public void setDataFieldId(Long dataFieldId) {
		this.dataFieldId = dataFieldId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@OneToOne(targetEntity = Organization.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = false, name = "organization_id")
	private Organization organizationId;

	public Organization getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Organization organizationId) {
		this.organizationId = organizationId;
	}

}