package com.heymom.backend.entity.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;
import com.heymom.backend.entity.incentive.CouponReceiveRecord;

@Entity
@Table(name = "user")
public class User extends BaseEntity {
	private List<ActivityAttendeeRecord> attendRecords;
	private List<CouponReceiveRecord> couponReceiveRecords;
	private String email;
	private List<ActivityAttendeeRecord> followRecords;
	private Integer gender;
	private Long id;
	private List<Kid> kids;
	private String mobile;
	private String name;
	private String password;
	private List<Role> roles;
	private Date tokenCreateTime;
	private UserInfo userInfo;
	private String userToken;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Where(clause = "status>=0")
	public List<ActivityAttendeeRecord> getAttendRecords() {
		return attendRecords;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Where(clause = "status>=0")
	public List<CouponReceiveRecord> getCouponReceiveRecords() {
		return couponReceiveRecords;
	}

	@Column(name = "email", unique = true, length = 50)
	public String getEmail() {
		return email;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Where(clause = "status>=0")
	public List<ActivityAttendeeRecord> getFollowRecords() {
		return followRecords;
	}

	@Column(name = "gender", precision = 1, scale = 0)
	public Integer getGender() {
		return gender;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@Where(clause = "status>=0")
	public List<Kid> getKids() {
		return kids;
	}

	@Column(name = "mobile", unique = true, nullable = false, length = 20)
	public String getMobile() {
		return mobile;
	}

	@Column(name = "name", unique = true, length = 50)
	public String getName() {
		return name;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return password;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	public List<Role> getRoles() {
		return roles;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "token_create_time", length = 50)
	public Date getTokenCreateTime() {
		return tokenCreateTime;
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Column(name = "token", length = 36)
	public String getUserToken() {
		return userToken;
	}

	public void setAttendRecords(List<ActivityAttendeeRecord> attendRecords) {
		this.attendRecords = attendRecords;
	}

	public void setCouponReceiveRecords(List<CouponReceiveRecord> couponReceiveRecords) {
		this.couponReceiveRecords = couponReceiveRecords;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFollowRecords(List<ActivityAttendeeRecord> followRecords) {
		this.followRecords = followRecords;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKids(List<Kid> kids) {
		this.kids = kids;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setTokenCreateTime(Date tokenCreateTime) {
		this.tokenCreateTime = tokenCreateTime;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
