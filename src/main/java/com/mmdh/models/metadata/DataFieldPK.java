package com.mmdh.models.metadata;

import java.io.Serializable;

import com.mmdh.models.Organization;

public class DataFieldPK implements Serializable {

	private static final long serialVersionUID = -7473128145450343012L;

	private Organization organizationId;
	private String name;
	private String locale;
	
	public Organization getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Organization organizationId) {
		this.organizationId = organizationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
