package com.apm.repos.models;

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
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the "Actor" database table.
 * 
 */
@Entity(name = "actor")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="actorId")
public class Actor extends AuditEntity implements Serializable {
	
	private static final long serialVersionUID = -4375742010527276532L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTOR_SEQ")
	@SequenceGenerator(name = "ACTOR_SEQ", sequenceName = "ACTOR_SEQ", allocationSize = 1)
	@Column(name="actor_id", unique=true, nullable=false)
	private Long actorId;

	@Column(name="actor_name", nullable=false)
	private String actorName;
	
	public Long getActorId() {
		return this.actorId;
	}

	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return this.actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="organization_id")
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}


}