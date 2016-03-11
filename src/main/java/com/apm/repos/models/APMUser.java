package com.apm.repos.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "apmuser")
public class APMUser implements UserDetails {

	private static final long serialVersionUID = 1607037058320546161L;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "isAccountNonExpired")
	private boolean isAccountNonExpired;

	@Column(name = "isAccountNonLocked")
	private boolean isAccountNonLocked;

	@Column(name = "isCredentialsNonExpired")
	private boolean isCredentialsNonExpired;

	@Column(name = "isEnabled")
	private boolean isEnabled;

	@Column(name = "invalidLoginCount")
	private Long invalidLoginCount;

	@Column(name = "lastLoginDate")
	private Date lastLoginDate;

	@Column(name = "locale")
	private Locale locale;

	public APMUser() {
		super();
		// init default variables
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;

		this.setInvalidLoginCount(0L);
		this.setLastLoginDate(GregorianCalendar.getInstance().getTime());
		this.setLocale(Locale.getDefault());
		this.isEnabled = false;
	}

	public Long getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return "BLOCKED";
	}

	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public Long getInvalidLoginCount() {
		return invalidLoginCount;
	}

	public void setInvalidLoginCount(Long invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}

	public String getLastLoginDate() {
		return lastLoginDate.toString();
	}

	public void setLastLoginDate(Date date) {
		this.lastLoginDate = date;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@CollectionId(columns = @Column(name = "role_user_id"), type = @Type(type = "long"), generator = "native")
	List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return buildUserAuthority(roles);
	}

	private List<GrantedAuthority> buildUserAuthority(List<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		if (userRoles != null) {
			// Build user's authorities
			for (Role userRole : userRoles) {
				setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
			}
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}
