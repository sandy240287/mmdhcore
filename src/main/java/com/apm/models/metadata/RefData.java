package com.apm.models.metadata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the "ref_data" database table.
 * 
 */
@Entity(name = "ref_data")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dataFieldId")
public class RefData extends AuditEntity implements Serializable {

	private static final long serialVersionUID = -8911616369331953134L;

	@Column(name = "data_key", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String dataKey;

	@Column(name = "data_value", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String dataValue;

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "data_field_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private DataField dataFieldId;

	public DataField getDataFieldId() {
		return dataFieldId;
	}

	public void setDataFieldId(DataField dataFieldId) {
		this.dataFieldId = dataFieldId;
	}


}