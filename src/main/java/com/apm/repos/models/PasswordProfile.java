package com.apm.repos.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The persistent class for the "Password_Recovery" database table.
 * 
 */
@Entity
public class PasswordProfile implements Serializable {
	
	private static final long serialVersionUID = 9161951022377668620L;

	@Id
	@Column(name="\"Password_Recovery\"", unique=true, nullable=false)
	private Long password_Recovery;

	@Column(name="audit_id")
	private Long auditId;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="recovery_token")
	private String recoveryToken;

	@Column(name="recovery_token_expired_flag")
	private Long recoveryTokenExpiredFlag;

	@Column(name="recovery_token_valid_upto")
	private Long recoveryTokenValidUpto;

	@Column(name="secret_answer_1")
	private String secretAnswer1;

	@Column(name="secret_answer_2")
	private String secretAnswer2;

	@Column(name="secret_answer_3")
	private String secretAnswer3;

	@Column(name="secret_question_1")
	private String secretQuestion1;

	@Column(name="secret_question_2")
	private String secretQuestion2;

	@Column(name="secret_question_3")
	private String secretQuestion3;
	
	@Column(name="user_id")
	private String userId;

	public Long getPassword_Recovery() {
		return this.password_Recovery;
	}

	public void setPassword_Recovery(Long password_Recovery) {
		this.password_Recovery = password_Recovery;
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRecoveryToken() {
		return this.recoveryToken;
	}

	public void setRecoveryToken(String recoveryToken) {
		this.recoveryToken = recoveryToken;
	}

	public Long getRecoveryTokenExpiredFlag() {
		return this.recoveryTokenExpiredFlag;
	}

	public void setRecoveryTokenExpiredFlag(Long recoveryTokenExpiredFlag) {
		this.recoveryTokenExpiredFlag = recoveryTokenExpiredFlag;
	}

	public Long getRecoveryTokenValidUpto() {
		return this.recoveryTokenValidUpto;
	}

	public void setRecoveryTokenValidUpto(Long recoveryTokenValidUpto) {
		this.recoveryTokenValidUpto = recoveryTokenValidUpto;
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