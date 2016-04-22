package com.apm.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the "password_profile" database table.
 * 
 */
@Entity(name = "password_profile")
@EntityListeners(AuditEntityListener.class)
public class PasswordProfile extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 9161951022377668620L;

	// default
	public PasswordProfile(){
		
	}
	// init blank
	public PasswordProfile(Long userId) {
		this.userId = userId;
		secretQuestion1 = "";
		secretAnswer1 = "";
		secretQuestion2 = "";
		secretAnswer2 = "";
		secretQuestion3 = "";
		secretAnswer3 = "";
	}

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "secret_answer_1")
	@JsonView(JSONView.ParentObject.class)
	private String secretAnswer1;

	@Column(name = "secret_answer_2")
	@JsonView(JSONView.ParentObject.class)
	private String secretAnswer2;

	@Column(name = "secret_answer_3")
	@JsonView(JSONView.ParentObject.class)
	private String secretAnswer3;

	@Column(name = "secret_question_1")
	@JsonView(JSONView.ParentObject.class)
	private String secretQuestion1;

	@Column(name = "secret_question_2")
	@JsonView(JSONView.ParentObject.class)
	private String secretQuestion2;

	@Column(name = "secret_question_3")
	@JsonView(JSONView.ParentObject.class)
	private String secretQuestion3;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSecretAnswer1() {
		return this.secretAnswer1;
	}

	public void setSecretAnswer1(String secretAnswer1) {
		this.secretAnswer1 = secretAnswer1;
	}

	public String getSecretAnswer2() {
		return this.secretAnswer2;
	}

	public void setSecretAnswer2(String secretAnswer2) {
		this.secretAnswer2 = secretAnswer2;
	}

	public String getSecretAnswer3() {
		return this.secretAnswer3;
	}

	public void setSecretAnswer3(String secretAnswer3) {
		this.secretAnswer3 = secretAnswer3;
	}

	public String getSecretQuestion1() {
		return this.secretQuestion1;
	}

	public void setSecretQuestion1(String secretQuestion1) {
		this.secretQuestion1 = secretQuestion1;
	}

	public String getSecretQuestion2() {
		return this.secretQuestion2;
	}

	public void setSecretQuestion2(String secretQuestion2) {
		this.secretQuestion2 = secretQuestion2;
	}

	public String getSecretQuestion3() {
		return this.secretQuestion3;
	}

	public void setSecretQuestion3(String secretQuestion3) {
		this.secretQuestion3 = secretQuestion3;
	}

}