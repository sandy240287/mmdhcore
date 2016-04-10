package com.apm.repos.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apm.repos.audit.AuditEntity;
import com.apm.repos.audit.AuditEntityListener;
import com.apm.utils.JSONView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "apmuser")
@EntityListeners(AuditEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class APMUser extends AuditEntity implements UserDetails {

	private static final long serialVersionUID = 1607037058320546161L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APMUSER_SEQ")
	@SequenceGenerator(name="APMUSER_SEQ",sequenceName="APMUSER_SEQ", allocationSize=1)
	@Column(name = "user_id")
	@JsonView(JSONView.ParentObject.class)
	private Long userId;

	@Column(name = "username", nullable = false)
	@JsonView(JSONView.ParentObject.class)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	@JsonView(JSONView.ParentObject.class)
	private String firstName;

	@Column(name = "last_name")
	@JsonView(JSONView.ParentObject.class)
	private String lastName;

	@Column(name = "middle_name")
	@JsonView(JSONView.ParentObject.class)
	private String middleName;

	@Column(name = "isAccountNonExpired")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private boolean isAccountNonExpired;

	@Column(name = "isAccountNonLocked")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private boolean isAccountNonLocked;

	@Column(name = "isCredentialsNonExpired")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private boolean isCredentialsNonExpired;

	@Column(name = "isEnabled")
	@JsonView(JSONView.ParentObject.class)
	private boolean isEnabled;

	@Column(name = "invalidLoginCount")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Long invalidLoginCount;

	@Column(name = "lastLoginDate")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	private Date lastLoginDate;

	@Column(name = "locale")
	@JsonView(JSONView.ParentObjectWithChildren.class)
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return "BLOCKED";
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

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	PasswordProfile passwordProfile;

	public PasswordProfile getPasswordProfile() {
		return passwordProfile;
	}

	public void setPasswordProfile(PasswordProfile passwordProfile) {
		this.passwordProfile = passwordProfile;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	@JsonView(JSONView.ParentObjectWithChildren.class)
	Role role;

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return buildUserAuthority(role);
	}

	private List<GrantedAuthority> buildUserAuthority(Role userRole) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		if (userRole != null) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

		return result;
	}

}
