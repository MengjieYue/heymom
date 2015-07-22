package com.heymom.backend.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.heymom.backend.entity.BaseEntity;

@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

	private String address;

	private User user;

	private long userID;

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	@OneToOne(mappedBy = "userInfo")
	public User getUser() {
		return user;
	}

	@Id
	@GeneratedValue(generator = "userInfoForeignKey")
	@GenericGenerator(name = "userInfoForeignKey", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Column(name = "user_id", nullable = false)
	public long getUserID() {
		return userID;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
