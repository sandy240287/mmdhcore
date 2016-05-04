package com.apm.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;

@Entity(name = "verification_token")
@EntityListeners(AuditEntityListener.class)
public class VerificationToken extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 8492632809900767987L;
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERIFICATION_SEQ")
	@SequenceGenerator(name = "VERIFICATION_SEQ", sequenceName = "VERIFICATION_SEQ", allocationSize = 1)
	private Long token_id;
	private String token;
	private Date expiryDate;
	private boolean verified;

	public VerificationToken() {
		super();
	}

	public VerificationToken(String token, APMUser user) {
		super();
		this.setToken(token);
		this.user = user;
		this.setExpiryDate(calculateExpiryDate(EXPIRATION));
		this.setVerified(false);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public Long getToken_id() {
		return token_id;
	}

	public void setToken_id(Long token_id) {
		this.token_id = token_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@OneToOne(targetEntity = APMUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = false, name = "token_id")
	private APMUser user;

	public APMUser getUser() {
		return user;
	}

	public void setUser(APMUser user) {
		this.user = user;
	}

}
